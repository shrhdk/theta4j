package com.theta360.cli;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.type.UINT32;
import com.theta360.theta.Theta;
import com.theta360.theta.ThetaEventListener;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

public final class ThetaCLI {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaCLI.class);

    private static final UINT32 SESSION_ID = new UINT32(1);

    private static UINT32 objectHandle;
    private static final CountDownLatch waitObjectAdded = new CountDownLatch(1);

    private ThetaCLI() {
    }

    private static PtpEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
            ThetaCLI.objectHandle = objectHandle;
            waitObjectAdded.countDown();
        }

        @Override
        public void onDevicePropChanged(UINT32 devicePropCode) {
            LOGGER.info("onDevicePropChanged: " + devicePropCode);
        }

        @Override
        public void onStoreFull(UINT32 storageID) {
            LOGGER.info("onStoreFull: " + storageID);
        }

        @Override
        public void onCaptureComplete(UINT32 transactionID) {
            LOGGER.info("onCaptureComplete: " + transactionID);
        }
    };

    private static CommandLine parseArgs(String[] args) throws ParseException {
        Options options = new Options();

        // output
        Option o = new Option("o", true, "File name of captured image.");
        o.setLongOpt("output");
        o.setType(String.class);
        options.addOption(o);

        // time
        Option t = new Option("t", false, "Get the date time of RICOH THETA.");
        o.setLongOpt("datetime");
        options.addOption(t);

        CommandLineParser parser = new PosixParser();
        return parser.parse(options, args);
    }

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        CommandLine cmd = parseArgs(args);

        try (Theta theta = new Theta()) {
            theta.addListener(listener);
            theta.getDeviceInfo();
            theta.openSession(SESSION_ID);

            if (cmd.hasOption("t")) {
                System.out.println(theta.getDateTime());
                return;
            }

            theta.initiateCapture();

            if (!cmd.hasOption("o")) {
                return;
            }

            waitObjectAdded.await();

            File file = new File(cmd.getOptionValue("o"));
            try (OutputStream output = new FileOutputStream(file)) {
                theta.getResizedImageObject(objectHandle, output);
            }
        }
    }
}
