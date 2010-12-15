/**
 * Copyright 2007 DFKI GmbH.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * This file is part of MARY TTS.
 *
 * MARY TTS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package marytts.tools.redstart;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * About dialog of Recording Session Manager
 */
public class About extends JDialog {

    /** Creates new form About */
    public About(JFrame parent) {
        super(parent,true);
        initComponents();
        pack();
        Rectangle parentBounds = parent.getBounds();
        Dimension size = getSize();
        // Center in the parent
        int x = Math.max(0, parentBounds.x + (parentBounds.width - size.width) / 2);
        int y = Math.max(0, parentBounds.y + (parentBounds.height - size.height) / 2);
        setLocation(new Point(x, y));

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel_CopyrightText = new javax.swing.JPanel();
        jLabel_ToolName = new javax.swing.JLabel();
        jLabel_VersionNum = new javax.swing.JLabel();
        jLabel_DFKILogo = new javax.swing.JLabel();
        jButton_OK = new javax.swing.JButton();
        jLabel_ToolIcon = new javax.swing.JLabel();
        jLabel_CopyrightDate = new javax.swing.JLabel();
        jLabel_MARYLink = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About Redstart");
        setResizable(false);
        jPanel_CopyrightText.setBackground(java.awt.Color.white);
        jLabel_ToolName.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel_ToolName.setText("Recording Session Manager");

        jLabel_VersionNum.setText("Version 1.0");

        jLabel_DFKILogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/marytts/tools/redstart/dfki_logo.jpg")));

        jButton_OK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/marytts/tools/redstart/ok_16x16.png")));
        jButton_OK.setText("OK");
        jButton_OK.setPreferredSize(new java.awt.Dimension(95, 25));
        jButton_OK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_OKActionPerformed(evt);
            }
        });

        jLabel_ToolIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/marytts/tools/redstart/redstop_48x48.png")));

        jLabel_CopyrightDate.setText("Copyright (c) 2007  DFKI GmbH");

        jLabel_MARYLink.setText("Visit the MARY TTS Home Page at http://mary.dfki.de");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/marytts/tools/redstart/mary_logo.png")));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setText("Redstart");

        org.jdesktop.layout.GroupLayout jPanel_CopyrightTextLayout = new org.jdesktop.layout.GroupLayout(jPanel_CopyrightText);
        jPanel_CopyrightText.setLayout(jPanel_CopyrightTextLayout);
        jPanel_CopyrightTextLayout.setHorizontalGroup(
            jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_CopyrightTextLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel_DFKILogo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 222, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel_MARYLink)
                    .add(jLabel_CopyrightDate))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton_OK, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel_CopyrightTextLayout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .add(jLabel_ToolIcon)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel_CopyrightTextLayout.createSequentialGroup()
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 29, Short.MAX_VALUE)
                        .add(jLabel_VersionNum))
                    .add(jLabel_ToolName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(75, 75, 75))
        );
        jPanel_CopyrightTextLayout.setVerticalGroup(
            jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_CopyrightTextLayout.createSequentialGroup()
                .add(39, 39, 39)
                .add(jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel_ToolIcon)
                    .add(jPanel_CopyrightTextLayout.createSequentialGroup()
                        .add(jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel_VersionNum)
                            .add(jLabel3))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel_ToolName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(41, 41, 41)
                .add(jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel_CopyrightTextLayout.createSequentialGroup()
                        .add(jLabel_CopyrightDate)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel_MARYLink))
                    .add(jLabel1))
                .add(19, 19, 19)
                .add(jPanel_CopyrightTextLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel_DFKILogo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 48, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton_OK, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_CopyrightText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel_CopyrightText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_OKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_OKActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton_OKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_OK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel_CopyrightDate;
    private javax.swing.JLabel jLabel_DFKILogo;
    private javax.swing.JLabel jLabel_MARYLink;
    private javax.swing.JLabel jLabel_ToolIcon;
    private javax.swing.JLabel jLabel_ToolName;
    private javax.swing.JLabel jLabel_VersionNum;
    private javax.swing.JPanel jPanel_CopyrightText;
    // End of variables declaration//GEN-END:variables

}

