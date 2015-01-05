package com.theta360.gui;

import com.theta360.ptp.PtpEventListener;
import com.theta360.ptp.PtpIpInitiator;
import com.theta360.ptp.data.GUID;
import com.theta360.ptp.type.UINT32;
import com.theta360.theta.Theta;
import com.theta360.theta.ThetaEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.UUID;

public class ThetaGUI extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThetaGUI.class);

    public static final UINT32 SESSION_ID = new UINT32(1);

    public static void main(String[] args) {
        JFrame thetaGUI = new ThetaGUI();
        thetaGUI.setVisible(true);
    }

    private static PtpEventListener listener = new ThetaEventListener() {
        @Override
        public void onObjectAdded(UINT32 objectHandle) {
            LOGGER.info("onObjectAdded: " + objectHandle);
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

    private JButton captureButton = new JButton() {
        {
            setText("capture");
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        PtpIpInitiator initiator = new PtpIpInitiator(new GUID(UUID.randomUUID()), Theta.IP_ADDRESS, Theta.TCP_PORT);
                        initiator.addListener(listener);
                        initiator.getDeviceInfo();
                        initiator.openSession(SESSION_ID);
                        initiator.initiateCapture();
                        initiator.closeSession();
                        initiator.close();
                    } catch (IOException e2) {
                        LOGGER.error(e2.getMessage(), e2);
                    }
                }
            });
        }
    };

    public ThetaGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(200, 200);
        getContentPane().add(captureButton);
    }
}
