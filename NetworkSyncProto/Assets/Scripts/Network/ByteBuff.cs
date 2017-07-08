/*
	FluorineFx open source library 
	Copyright (C) 2007 Zoltan Csibi, zoltan@TheSilentGroup.com, FluorineFx.com 
	
	This library is free software; you can redistribute it and/or
	modify it under the terms of the GNU Lesser General Public
	License as published by the Free Software Foundation; either
	version 2.1 of the License, or (at your option) any later version.
	
	This library is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
	Lesser General Public License for more details.
	
	You should have received a copy of the GNU Lesser General Public
	License along with this library; if not, write to the Free Software
	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
/// <summary>
/// This type supports the Fluorine infrastructure and is not intended to be used directly from your code.
/// http://java.sun.com/j2se/1.5.0/docs/api/java/nio/ByteBuffer.html
/// 
/// The following invariant holds for the mark, position, limit, and capacity values: 
/// 0 lte mark lte position lte limit lte capacity 
/// </summary>
[CLSCompliant(false)]
public class ByteBuffer
{
    List<byte> buffer;
    /// <summary>
    /// Initializes a new instance of the ByteBuffer class.
    /// </summary>
    /// <param name="stream">Wraps the MemoryStream into a buffer.</param>
    public ByteBuffer(List<byte> buffer)
    {
        this.buffer = buffer;
    }

    private int _bookmark = -1;
    private int position = 0;
    /// <summary>
    /// Allocates a new byte buffer.
    /// The new buffer's position will be zero, its limit will be its capacity, 
    /// and its mark will be undefined. 
    /// It will have a backing array, and its array offset will be zero. 
    /// </summary>
    /// <param name="capacity"></param>
    /// <returns></returns>


    /// <summary>
    /// Wraps a byte array into a buffer.
    /// The new buffer will be backed by the given byte array; that is, modifications 
    /// to the buffer will cause the array to be modified and vice versa. 
    /// The new buffer's capacity will be array.length, its position will be offset, 
    /// its limit will be offset + length, and its mark will be undefined.
    /// </summary>
    /// <param name="array">Byte array to wrap.</param>
    /// <param name="offset">Offset in the byte array.</param>
    /// <param name="length"></param>
    /// <returns></returns>
    public static ByteBuffer Wrap(byte[] array, int offset, int length)
    {
        return new ByteBuffer(array.ToList().GetRange(offset, length).ToList());
    }

    public int Count
    {
        get
        {
            return buffer.Count;
        }
    }
    /// <summary>
    /// Wraps a byte array into a buffer. 
    /// The new buffer will be backed by the given byte array; that is, modifications 
    /// to the buffer will cause the array to be modified and vice versa. 
    /// The new buffer's capacity and limit will be array.length, its position will be zero,
    /// and its mark will be undefined.
    /// </summary>
    /// <param name="array"></param>
    /// <returns></returns>
    public static ByteBuffer Wrap(byte[] array)
    {
        return Wrap(array, 0, array.Length);
    }

    /// <summary>
    /// Turns on or off autoExpand
    /// </summary>
    public bool AutoExpand
    {
        get
        {
            return true;
        }
    }
    /// <summary>
    /// Returns this buffer's capacity.
    /// </summary>
    public int Capacity
    {
        get { return buffer.Capacity; }
    }
    /// <summary>
    /// Returns this buffer's limit. 
    /// </summary>

    public long Mark()
    {
        _bookmark = this.position;
        return _bookmark;
    }
    /// <summary>
    /// Clears the current bookmark.
    /// </summary>
    public void ClearBookmark()
    {
        _bookmark = -1;
    }
    /// <summary>
    /// Resets this buffer's position to the previously-marked position. 
    /// Invoking this method neither changes nor discards the mark's value. 
    /// </summary>
    public void Reset()
    {
        if (_bookmark != -1)
            this.position = _bookmark;
    }
    /// <summary>
    /// Clears this buffer. The position is set to zero, the limit is set to the capacity, and the mark is discarded.
    /// </summary>
    public void Clear()
    {
        ClearBookmark();
        position = 0;
    }

    /// <summary>
    /// Flips this buffer. The limit is set to the current position and then 
    /// the position is set to zero. If the mark is defined then it is discarded.
    /// </summary>

    /// <summary>
    /// Rewinds this buffer. The position is set to zero and the mark is discarded.
    /// </summary>
    public void Rewind()
    {
        ClearBookmark();
        this.position = 0;
    }


    public byte Get(int i)
    {
        return (byte)this.GetByte(i);
    }
    /// <summary>
    /// Reads a 4-byte signed integer using network byte order encoding.
    /// </summary>
    /// <returns>The 4-byte signed integer.</returns>
    public int GetInt()
    {
        // Read the next 4 bytes, shift and add
        byte[] bytes = this.GetBytes(4);
        return ((bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | bytes[3]);
    }
    /// <summary>
    /// Reads a 2-byte signed integer using network byte order encoding.
    /// </summary>
    /// <returns>The 2-byte signed integer.</returns>
    public short GetShort()
    {
        //Read the next 2 bytes, shift and add.
        byte[] bytes = this.GetBytes(2);
        return (short)((bytes[0] << 8) | bytes[1]);
    }


    public byte[] ReadBytes(int count)
    {
        byte[] bytes = new byte[count];
        for (int i = 0; i < count; i++)
        {
            bytes[i] = (byte)this.ReadByte();
        }
        return bytes;
    }



    public byte[] GetBytes(int count)
    {
        byte[] bytes = new byte[count];
        for (int i = 0; i < count; i++)
        {
            bytes[i] = (byte)this.GetByte(i);
        }
        return bytes;
    }



    public bool Readable()
    {
        return buffer.Count > 0;
    }

    public int ReadableBytes()
    {
        return buffer.Count;
    }
    
    public byte ReadByte()
    {
        if (!Readable())
        {
            throw new InvalidOperationException("buffer not readable");
        }
        byte b = buffer[0];
        buffer.RemoveAt(0);
        return b;
    }

    public byte GetByte(int i)
    {
        if (!Readable())
        {
            throw new InvalidOperationException("buffer not readable");
        }
        byte b = buffer[i];
        return b;
    }

    public int ReadUInt24()
    {
        byte[] bytes = this.ReadBytes(3);
        int value = bytes[0] << 16 | bytes[1] << 8 | bytes[2];
        return value;
    }

    public int ReadInt()
    {
        byte[] bytes = this.ReadBytes(4);
        return ((bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | bytes[3]);       
    }


    public float ReadFloat()
    {
        byte[] b = ReadBytes(4);
        Array.Reverse(b);
        return BitConverter.ToSingle(b, 0);
    }

    public long ReadLong()
    {
        byte[] bytes = ReadBytes(8);
        return ((bytes[7] << 56) | (bytes[6] << 48) | (bytes[6] << 40) | (bytes[5] << 32) | (bytes[4] << 24) | (bytes[5] << 16) | (bytes[6] << 8) | bytes[7]);
    }


    public void Write(byte[] bytes)
    {
        buffer.AddRange(bytes);
    }


    public void Write(byte[] bytes, int index, int length)
    {
        buffer.AddRange(bytes.ToList().GetRange(index, length));
    }

    /// <summary>
    /// Reads a 4-byte signed integer.
    /// </summary>
    /// <returns>The 4-byte signed integer.</returns>
    public int ReadReverseInt()
    {
        byte[] bytes = this.ReadBytes(4);
        int val = 0;
        val += bytes[3] << 24;
        val += bytes[2] << 16;
        val += bytes[1] << 8;
        val += bytes[0];
        return val;
    }

}