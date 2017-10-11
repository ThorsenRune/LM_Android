//folder: https://drive.google.com/drive/u/0/folders/0B5pCUAt6BabuU1I1Ri1zNzA4SG8
//Doc: https://docs.google.com/document/d/1R5-SPit_fAFV2P3UILvL1Y-MrLzK05l34V1ZhRiZrFY/edit

package it.fdg.lm;
//				A Dummy module Containng description of the SW 
/**
History:
Cleaning process 
	cSerial5	171004
	cProtocol3	171004

Entry points:
 fmain.onCreate->mEntryPoint				//Principal entry point
 cProgram3.mCommunicate(true)->mPeriodicProcessing			//Activate communication with device


 mPersistAllData	(171005A)
 /*   Persistent data for the application. Get/Set current values  to static storage
 *  setsPrefFileName("LM_AppSetting");     //Global settings
 *   sProtFileName=mPrefs5(bGet,"Protocol_Settings_File", sProtFileName);   //Current file for  protocol settings
 * */


import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import static it.fdg.lm.cProgram3.mProtocolPrepare;
import static it.fdg.lm.cProgram3.nAppProps;
import static it.fdg.lm.cProgram3.oProtocol;
import static it.fdg.lm.cProgram3.sDevices1;

/**Main program methods
		*
		*R171005
 cElemViewProps		define how a control is used, what element is associated , max values

		**cProtocol3
		---------------------
		n->m*cProtElem


		=====================
		*cSerial5-Handles serial communication cProtocol3 1<->1cSerial
		---------------------
		*Handles bluetooth transmissions
		*Uses 2*cFIFO
		*
		*
		*
		*
		*
		*
		cProtocol3.mAsyncProcessing_Work->mConnect->


		oSerial.mConnect:Starts connection

		mStartServerService<start listening for a client.The purpose is to be able to daisyuchain devices
		so you can remotely monitor and control a user.Hierachy Client->server->device
		*
		mConnect2Device->mConnect2Device_Sub
		//Make the serial connection


		*
		*
		*
		*
		*
		*
		*
		*/

/**********************HowTo Debug *********************/
//	Find control: use mTouchListening

/*USERS MANUAL
/*
	Layout:
		Vertical Sliders
		Horizontal Sliders
		Signal

Connect/activate connection
		Touch the Connect:LM--- on the top menu


ON slider, swipe to change value (0) (-) handles are visual only
		to change value (longpress, doubletap or tap spacebar on the bottom)
ON signal, swipe LR to activate other signal view

*/














 
 /*			OBSOLETE METHODS
    public String mReadString() {
        String s="";
        nBytesInBuffer= cSerial5.nBytesAvailable(oInputStream);
        if (nBytesInBuffer > 0) {
            for (int j = 0; j < nBytesInBuffer; j++) {
                try {
                    s += "," + oInputStream.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }       //Read a string from stream


     /**
     * Makes this device discoverable for 300 seconds (5 minutes).

private void ensureDiscoverable() {
		if (oBTAdapter.getScanMode() !=
		BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		//discoverableIntent.setClass(mContext);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		mContext.startActivity(discoverableIntent);
		}
		}


public int mReadByte00(InputStream oI) {        //To refactor into cProtocol 170929 // TODO: 29/09/2017
		int nAvail=0;
		try {
		nAvail = oI.available();
		} catch (IOException e) {
		e.printStackTrace();
		}
		if (nAvail>0)
		try {
		int data= oI.read();      //Remmeber that read is blocking
		return data;
		} catch (IOException e) {
		e.printStackTrace();
		}
		return -1;
		}
public void mFlush() {
		if (this.oInputStream ==null) return;
		try {
		this.oInputStream.skip(oInputStream.available());
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
		*/

class tDescriptions {
private static void mAlignProtocolLengthWithDevicesCount() {
		if (oProtocol.length<sDevices1.length){
		mProtocolPrepare(sDevices1.length);
		}
		if (oProtocol.length>sDevices1.length){
		//        sDevices1=mArrayRedim(sDevices1,oProtocol.nDataLength-1);
		}
		}
	public static void mToast(Context mContext, String msg){
		Toast.makeText(cProgram3.mContext, msg, Toast.LENGTH_LONG).show();
	}
	public static float dispVal2unit(float dvval, int mySliderMax, float[] nDisplayRange) {      //convert display values to units
		dvval = dvval / mySliderMax;                //Slider value to fraction
		dvval = dvval * (nDisplayRange[1] - nDisplayRange[0]);   //Scale to visible range
		return (dvval + nDisplayRange[0]);                   //Add the offset
	}


	private int bPrivileges() {
		return nAppProps[cKonst.eNum.kPrivileges.ordinal()];
	}
	Context mContext;
	public int dpToPx(int dp) {
		float scaledSizeInPixels;

		scaledSizeInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,                dp, mContext.getResources().getDisplayMetrics());
//        scaledSizeInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT,                dp, mContext.getResources().getDisplayMetrics());
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
		return (int) scaledSizeInPixels;
	}

}