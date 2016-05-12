package god.codename.brightside;

/**
 * Created by Loknath Shankar on 5/9/2016.
 */
public class CODES {
    public static int NO_NEWS=-2;
    public static int LOAD_MORE=1;


    /**Netowrk related coded**/
    public static int TIMEOUT=-100;
    public static int MALFORMED_URL=-101;
    public static int HTTP_STATUS=-102;
    public static int UNSUPPORTED_MIME=-103;
    public static int IO_EXP=-104;
    public static int OK=200;


    public static int INVALID_TOKEN=-105;
    public static String ResponseCode(int CODE){
        String returnString="Unknown Code";
        /**
         Code -1:Nothing
         **/
        /**
         Code -2:Load more url or next url is "NULL"
         **/

        if(CODE==NO_NEWS)
            returnString="No more news avaliable to load.";

        /**
         * Code 1 : Loading more news...
         */
        if(CODE == LOAD_MORE)
            returnString="Loading more news...";


        if(CODE == IO_EXP)
            returnString="Error while downloading news.\r\nPlease check your network";
        if(CODE == HTTP_STATUS)
            returnString="Error while downloading news...(HTTP_STATUS)";
        if(CODE == TIMEOUT)
            returnString="Internet connection timeout.\r\nPlease check your network";
        if(CODE == MALFORMED_URL)
            returnString="Invalid URL";
        if(CODE == UNSUPPORTED_MIME)
            returnString="Error while downloading news...(MIME)";

        if(CODE == OK)
            returnString="OK";

        if(CODE == INVALID_TOKEN)
            returnString="INVALID TOKEN";

        return (returnString);
    }
}
