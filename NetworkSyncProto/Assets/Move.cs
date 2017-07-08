using UnityEngine;
using System.Collections.Generic;
using System.Collections;
using System;
public class Move : MonoBehaviour {

	// Use this for initialization
    //public float speed = 10f;

    public  float sampleFrequencyPersecond = 33;


    public float Tick
    {
        get
        {
            return (float)1.0 / sampleFrequencyPersecond;
        }
    }
    public float tick;

	public float startTime = 0;

    public float tickBegin = 0;

	void Start () {
		Debug.Log ("Start");

        StartCoroutine(SendCommand());
        StartCoroutine(ReceiveCommand());
		startTime  = Time.timeSinceLevelLoad;	
	}

    private Queue<Vector2> sendCmdQueue = new Queue<Vector2>();

    private Queue<Vector2> receiveCmdQueue = new Queue<Vector2>();
 
	float timeStatisticsBegin = 0; //1000ms
	int sampleInput = 0;

	// Update is called once per frame
	void Update () 
    {
		//Debug.LogWarning (Time.deltaTime);
		timeStatisticsBegin += Time.deltaTime;

		if (Input.GetKeyDown (KeyCode.LeftArrow) || Input.GetKeyDown (KeyCode.RightArrow) || Input.GetKeyDown (KeyCode.UpArrow) || Input.GetKeyDown (KeyCode.DownArrow) || Input.anyKey) {

			if (tickBegin < Tick) {
				tickBegin += Time.deltaTime;
			} else {
				float x = Input.GetAxis ("Horizontal");//*Time.deltaTime*speed ;
				float y = Input.GetAxis ("Vertical");//*Time.deltaTime*speed;
				tickBegin = tickBegin - Tick < 0 ? 0 : tickBegin - Tick;
				sendMessage(new Vector2(x, y));
				sampleInput++;
				if (sampleInput% 33 == 0) {
					Debug.LogWarning (timeStatisticsBegin);
				}
			}

			if (receiveCmdQueue.Count > 0) {
				//Debug.Log ("receiveCmdQueue");
				var vector = receiveCmdQueue.Dequeue ();
				transform.position = vector;  //directly set player posotion
			}
		}

        //Debug.Log(string.Format("x:{0}, y:{1}",x, y));
        //transform.Translate(x, y, 0);
	}

    IEnumerator SendCommand()
    {
        while (true)
        {
            if (sendCmdQueue.Count > 0)
            {
                Vector2 vector = sendCmdQueue.Dequeue();
                sendMessage(vector);
            }
            yield return null;
        }
    }

    IEnumerator ReceiveCommand()
    {
        while (true)
        {
			yield return null;
            Vector3 vector;
            if (receiveMessage(out vector) == true)
            {
                receiveCmdQueue.Enqueue(vector);
                Debug.Log(string.Format("receive network message x:{0}, y:{1}", vector.x, vector.y));
            }
        }
    }




    public static byte[] GetBigEndinByte(object t)
    {
        if (t is int)
        {
            byte[] a = System.BitConverter.GetBytes((int)t);   //得到小端字节序数组
            Array.Reverse(a);   //反转数组转成大端。
            return a;
        }
        if (t is float)
        {
            byte[] a = System.BitConverter.GetBytes((float)t);   //得到小端字节序数组
            Array.Reverse(a);   //反转数组转成大端。
            return a;
        }
        if (t is bool)
        {
            byte[] a = System.BitConverter.GetBytes((bool)t);   //得到小端字节序数组
            Array.Reverse(a);   //反转数组转成大端。
            return a;
        }
        throw new Exception("error basic data type");
    }
    public void sendMessage(Vector2 vector)
    {
        List<byte> b = new List<byte>(16);
        b.AddRange(GetBigEndinByte(16));
        b.AddRange(GetBigEndinByte(1));
        b.AddRange(GetBigEndinByte(vector.x));
        b.AddRange(GetBigEndinByte(vector.y));
        Debug.Log(string.Format("x:{0} y:{1}", vector.x,vector.y));
        SocketClient.outgoing.Enqueue(b.ToArray());
        //networkQueue.Enqueue(vector);
    }

   private Queue<Vector2> networkQueue = new Queue<Vector2>();

   public bool receiveMessage(out Vector3 vector )
   {
       if (SocketClient.incomming.Count > 0)
       {
           object cmd = SocketClient.incomming.Dequeue();
           if (cmd is Vector3) //place;
           {
               vector = (Vector3)cmd;
               return true;
           }
       }
       vector = Vector3.zero;
       return false;
   }
}
