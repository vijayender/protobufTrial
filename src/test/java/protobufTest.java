/**
 * Created by vijayender on 2/25/16.
 */
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.kvijayender.logp.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class protobufTest {
    // Only works in Linux.
    static String FPATH_PREFIX = "/tmp/protobufTrial";
    public LogP.Log getMessage(int i) {
        return LogP.Log.newBuilder().setId(i).setMessage("Hello " + i).build();
    }

    @DataProvider(name = "msgIdProvider")
    public static Object[][] someMsgIds() {
        return new Object[][] {{1},
                               {2},
                               {3},
                               {4}};
    }

    @Test(dataProvider = "msgIdProvider")
    public void testWriteAndReadObjectToFile(int msgId) throws IOException {
        String filePath = FPATH_PREFIX + "-1";
        // Open file
        FileOutputStream messageDest = new FileOutputStream(filePath);
        // Write to file
        messageDest.write(getMessage(msgId).toByteArray());
        // close file
        messageDest.close();
        // Open file
        FileInputStream messageSource = new FileInputStream(filePath);
        // Read object from file
        LogP.Log incomingLog = readObjectFromFile(messageSource);
        assert( incomingLog.equals(getMessage(msgId)));
        messageSource.close();
    }

    private LogP.Log readObjectFromFile(FileInputStream messageSource) throws IOException {
        return LogP.Log.parseFrom(messageSource);
    }

    @DataProvider(name = "msgIdsProvider")
    public Object[][] getMsgIdsS () {
        return new Object[][] {{new int[]{1,2,3,4,5}},
                               {new int[]{0,9,8,7}}};
    }

    /**
     * Write sequentially the messages generated with getMessage()
     * into a file.
     *
     * Read back the messages from the file and make sure the
     * contents are as expected.
     *
     * @param msgIds
     * @throws IOException
     */
    @Test(dataProvider = "msgIdsProvider")
    public void testMultiObjectReadAndWrite(int msgIds[]) throws IOException {
        String filePath = FPATH_PREFIX + "-2";
        FileOutputStream messageDest = new FileOutputStream(filePath);
        /**
         * https://developers.google.com/protocol-buffers/docs/techniques#streaming
         *
         * Protobuf has no information of the end of the message. Thus writing
         * multiple messages sequentially onto a file would fail while reading.
         *
         * The above link talks about writing to a stream as a TLV.
         *
         *   However the comment on
         *   http://kirkwylie.blogspot.in/2008/08/google-protocol-buffers-and-streaming.html
         *   brings to light the new api available writeDelimiteTo and parseDelimitedFrom which
         *   solves the problem
         *
         */

        for(int msgId : msgIds) {
            getMessage(msgId).writeDelimitedTo(messageDest);
        }

        // Close the file stream
        messageDest.close();

        FileInputStream inputStream = new FileInputStream(filePath);
        for(int msgId : msgIds) {
            /**
             * Clearly you have to know which message you are decoding.
             *   Hence the format if the messages are of different kinds
             *   should be prefixed with the message type such as:
             *     message_type, message, message_type, message.
             */
            LogP.Log incomingLog = LogP.Log.parseDelimitedFrom(inputStream);
            //LogP.Log incomingLog = messageSource.readMessage(LogP.Log.newBuilder(), );
            assert( incomingLog.equals(getMessage(msgId)) );
        }

        // Lets make sure we have reached the end of buffer
        assert (inputStream.available() == 0);
    }
}
