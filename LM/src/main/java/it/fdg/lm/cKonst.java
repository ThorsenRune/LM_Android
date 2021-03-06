//Doc:  https://docs.google.com/document/d/1hba4rFO9x6oHt7Qxqj9Skk9aJn7n1qS5JlBLx-Y7SA8/edit

package it.fdg.lm;
/*  Class of constants used in the application
 * Created by rthorsen on 05/10/2017.
 */

public class cKonst {
    //User levels

    public static       String sKeyFieldSep=".";

    public enum eProtState{                         //States of the protocol        (171012 refactored to here)
        kUnconnected2,                              //No serial connection
        //We are connected but waiting for device to send packs exposing the protocol
        kProtUndef1,                                 //The protocol is undefined and cannot be used, device disconnected
        kProtReady,                                 //Protocol is readyto use
        kProtResetReq1,                              //Do a reset of the protocol
        kProtInitInProgress,                        //Request for reset of the protocol, kCommInit will be sent to device
        kProtInitDone,                              //Protocol is loaded and ready to mStartListening
        //The protocol has just been filled with exposed variables and is ready to use
        kProtInitFailure,
        //Error in protocol initialization
        kProtTimeOut,
        kProtError,
        kRelay,
        kDoConnect1,
        kDoDisconnect;                          //Request a disconnection
    }
    //Serial communication states
    enum eSerial{
        //  cSerial5 nState_Serial constants
        kBT_ConnectReq1,                                 //Request to mConnect to device
        kBT_InvalidBT1,                                  //Cant open bluetooth, servere problem
        kBT_Disconnected,                                //No device is associated
        kBT_InvalidDevice1,                              //Device not paired

        kBT_Connected1,                                 //device is connected
        kDeviceWasSelected,                             //A device was selected and paired
        kBT_Undefined,                                  //Unconnected - waiting for some BT event
        kBT_TimeOut,
         kConnectionError,

        kBT_BrokenConnection,             //Device switched off !+ ideally we should listen for when it switches on again
        kListening, kListenAccepted,
         kOverflow,
        kBT_Connecting            //A client is connected, this is now also a server
     }
    /*Make the serial connection
        mConnectDeviceWithName(new name) ->sDeviceName,kBT_ConnectReq
        1.  kBT_ConnectReq1
        2.  isOpen,    doOpen,      kBT_InvalidBT1
*/
    public static int[] nAppProps={0,0,0,0,0,1}; //170904level of permissions given to user
    public enum eAppProps {     //Application properties enumerator
        kBlockScrolling,
        kAutoConnect,
        kZoomEnable,
        kShowDataPanel
        }

    enum bitmask {
        nil1;
        static int kDebug=1<<3;    //  Debug level of user level
    }

    static int nTextSize=15;
    enum eTexts {     //
        a;
        public static String txtMenu_showVertical ="Show Vertical Sliders";
        public static String txtDevice_DoConnect    ="Switch on device and press here";
        public static String txtDevice_Connecting   ="Connecting ";
        public static String txtDevice_TimeOut      ="Timeout in connection, Retry ";
        public static String txtDevice_Initializing ="Initializing protocol";
        public static String txtDevice_LostContact  ="Device lost contact, reconnect?";

    }
}
