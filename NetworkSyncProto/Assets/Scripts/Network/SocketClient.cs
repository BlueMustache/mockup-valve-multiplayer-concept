using UnityEngine;
using System.Collections;
using System.Net.Sockets;
using System.Net;
using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
public class SocketClient : MonoBehaviour {

    ByteBuffer buffer = new ByteBuffer(new List<byte>(4096));
    NetworkStream stream;
    public class IncommingMessageQueue: Queue
    {
    
    }

    public class OutGoingMessageQueue: Queue<byte[]>
    {

    }

    Queue<byte> receiveBuffer = new Queue<byte>();


	private TcpClient client;
    public static IncommingMessageQueue incomming = new IncommingMessageQueue();
    public static OutGoingMessageQueue outgoing = new OutGoingMessageQueue();
    

	public bool Connected{ get; private set; }
	// Use this for initialization
	void Start () {
		client = new TcpClient ();
       
        client.Connect("127.0.0.1", 8077);
	}

	public void Connect(string ip, int port){
		client.Connect(new IPEndPoint(IPAddress.Parse(ip), port));
	}

	public void Send(byte[] data)
	{
		client.GetStream ().Write (data, 0, data.Length);
	}

    void OnConnectedChanged(bool status)
    {
        Debug.Log("Connect Status:" + status);
        if (Connected)
        {
            stream = client.GetStream();
        }
    }
	// Update is called once per frame
	void Update () {
		var connectStatus = client.Connected;
        if (connectStatus != Connected)
        {
            Connected = connectStatus;
            OnConnectedChanged(connectStatus);
        }
        if (Connected)
        {
            byte[] data = new byte[1024];
            if (outgoing.Count > 0)
            {
                if (stream.CanWrite)
                {
                    var buf = outgoing.Dequeue();
                    stream.Write(buf, 0, (int)buf.Length);
                }
            }            
            if (stream.CanRead)
            {
                if(stream.DataAvailable){
                    byte[] bytes = new byte[1024];

                    int size = stream.Read(bytes, 0, 1024);
                    buffer.Write(bytes, 0, size);
                }
            }
            DispatchMessage();
        }
	}

	void DispatchMessage(){
        
        if(buffer.Count < 4){
            return;
        }

        int totalLength = buffer.GetInt();
        if (buffer.ReadableBytes() < totalLength )
        {
            return;
        }
        else
        {
            buffer.ReadInt();
        }
        int type = buffer.ReadInt();

           switch (type){
            case 2:
                float x = buffer.ReadFloat();
                float y = buffer.ReadFloat();             
                float z = buffer.ReadFloat();
                incomming.Enqueue(new Vector3(x, y, z));
                break;
            default:
                break;
         }

//        case 1:
//            Vector3 position = new Vector3 (incomming.ReadFloat(), incomming.ReadFloat(), incomming.ReadFloat());
//            long timestamp = incomming.ReadLong ();
//            //pass position back to client;
//            break;
//        default:
////			logger.warn("invalid message type {}", type);
//            incomming.ReadBytes(totalLength - 8); //read and throw the invalid message;
//            break;
//        }
		
	}
    void OnApplicationQuit()
    {
        stream.Close();
        client.Client.Close();
    }

}
