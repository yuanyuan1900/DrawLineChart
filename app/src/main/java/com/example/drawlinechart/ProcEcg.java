package com.example.drawlinechart;//package com.example.bluetoothtest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by zouyuan on 2017/3/24.
 */

public class ProcEcg {

    static final int fs = 500;
    private String dataRec;

    public ProcEcg(String data) throws Exception {
        dataRec = data;
    }

    public String getDataRec() {
        return dataRec;
    }

    public void procData() throws Exception {

        Log.d("DDD", "procData: " + dataRec);




        String strBlank = dataRec.trim();
        String[] strSplit = strBlank.split(" ");
        Log.d("DDD", "procData: " + strBlank);
        StringBuilder strTemp = new StringBuilder("");

        int dataDec = 0;
        String strDec = "";
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < strSplit.length; i++) {

            //System.out.println(strSplit[i].toString());
            Log.d("EEE", "procData: " + strSplit[i].toString());
            strTemp.append(strSplit[i].toString());
            i++;
//            System.out.println(strSplit[i].toString());
            Log.d("EEE", "procData: " + strSplit[i].toString());
            strTemp.append(strSplit[i].toString());

//            System.out.println(strTemp.toString());
            Log.d("EEE", "procData: " + strTemp.toString());
            dataDec = Integer.parseInt(strTemp.toString(), 16);
//            System.out.println(dataDec);
            Log.d("EEE", "procData: " + dataDec);
            strDec = String.valueOf(dataDec);
            //         System.out.println(strDec);
            Log.d("EEE", "procData: " + strDec);
            list.add(strDec);
            strTemp = new StringBuilder("");
        }

        //yy
        //然后存到数组中
        int[] wecg = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String str1 = list.get(i);
            str1 = str1.replace(" ", "");
            wecg[i] = Integer.parseInt(str1);
            //System.out.println(i+":"+arr[i]);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //开始滤波
        int numcc = wecg.length;
        if (numcc >= 30 * fs) {
            Filter fRet = new Filter();
            fRet.wecg = wecg;
            fRet.filter();
            /*FileWriter writer = new FileWriter("/Users/zouyuan/ZYWORK/MyASProject/data/wecgfirdata.txt");
            BufferedWriter bw = new BufferedWriter(writer);
			for(int i = 0; i < fRet.wecgfirdata.length; i++) {
				String str2 = String.valueOf(fRet.wecgfirdata[i]);
				bw.write(str2);
				bw.newLine();
			}
			bw.close();
			writer.close();
			writer = new FileWriter("/Users/zouyuan/ZYWORK/MyASProject/data/wecg2a.txt");
			bw = new BufferedWriter(writer);
			for(int i = 0; i < fRet.wecg2a.length; i++) {
				String str2 = String.valueOf(fRet.wecg2a[i]);
				bw.write(str2);
				bw.newLine();
			}
			bw.close();
			writer.close();*/
            Report rep = new Report();
            rep.ecgin = fRet.wecgfirdata;
            rep.ain = fRet.wecg2a;
            rep.report();
        } else {
            System.out.println("心电数据采集时间不足");
        }
    }

}
