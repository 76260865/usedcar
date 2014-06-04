package com.jason.usedcar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

/**
 * class name��FileService<BR>
 * class description��android�ļ���һЩ��ȡ����<BR>
 * PS�� <BR>
 */
public class FileService {
    private Context context;

    public FileService(Context c) {
        this.context = c;
    }

    // ��ȡsd�е��ļ�
    public String readSDCardFile(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        String result = streamRead(fis);
        return result;
    }

    // ��resĿ¼�½���һ��raw��Դ�ļ��У�������ļ�ֻ�ܶ�����д�롣����
    public String readRawFile(int fileId) throws IOException {
        // ȡ��������
        InputStream is = context.getResources().openRawResource(fileId);
        String result = streamRead(is);// ����һ���ַ���
        return result;
    }

    private String streamRead(InputStream is) throws IOException {
        int buffersize = is.available();// ȡ�����������ֽڳ���
        byte buffer[] = new byte[buffersize];
        is.read(buffer);// �����ݶ�������
        is.close();// ��ȡ��Ϻ�Ҫ�ر�����
        String result = EncodingUtils.getString(buffer, "UTF-8");// ����ȡ�õ����ݱ��룬��ֹ����
        return result;
    }

    // ��assets�ļ����µ��ļ���ͬ����ֻ�ܶ�ȡ����д��
    public String readAssetsFile(String filename) throws IOException {
        // ȡ��������
        InputStream is = context.getResources().getAssets().open(filename);
        String result = streamRead(is);// ����һ���ַ���
        return result;
    }

    // ��sd����д���ļ�
    public void writeSDCardFile(String path, byte[] buffer) throws IOException {
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(buffer);// д��buffer���顣�����д��һЩ�򵥵��ַ������Խ�String.getBytes()��д���ļ�;
        fos.close();
    }

    // ���ļ�д��Ӧ�õ�data/data��filesĿ¼��
    public void writeDateFile(String fileName, byte[] buffer) throws Exception {
        byte[] buf = fileName.getBytes("iso8859-1");
        fileName = new String(buf, "utf-8");
        // Context.MODE_PRIVATE��ΪĬ�ϲ���ģʽ��������ļ���˽�����ݣ�ֻ�ܱ�Ӧ�ñ�����ʣ��ڸ�ģʽ�£�д������ݻḲ��ԭ�ļ������ݣ���������д�������׷�ӵ�ԭ�ļ��С�����ʹ��Context.MODE_APPEND
        // Context.MODE_APPEND��ģʽ�����ļ��Ƿ���ڣ����ھ����ļ�׷�����ݣ�����ʹ������ļ���
        // Context.MODE_WORLD_READABLE��Context.MODE_WORLD_WRITEABLE������������Ӧ���Ƿ���Ȩ�޶�д���ļ���
        // MODE_WORLD_READABLE����ʾ��ǰ�ļ����Ա�����Ӧ�ö�ȡ��MODE_WORLD_WRITEABLE����ʾ��ǰ�ļ����Ա�����Ӧ��д�롣
        // ���ϣ���ļ�������Ӧ�ö���д�����Դ��룺
        // openFileOutput("output.txt", Context.MODE_WORLD_READABLE +
        // Context.MODE_WORLD_WRITEABLE);
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);// ������ļ�����
        fos.write(buffer);
        fos.close();
    }

    // ��ȡӦ�õ�data/data��filesĿ¼���ļ�����
    public String readDateFile(String fileName) throws Exception {
        FileInputStream fis = context.openFileInput(fileName);
        String result = streamRead(fis);// ����һ���ַ���
        return result;
    }
}