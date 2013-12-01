/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package marytts.tools.newinstall;

import java.awt.Dimension;

import org.apache.log4j.Logger;

import marytts.tools.newinstall.objects.Component;
import marytts.tools.newinstall.objects.VoiceComponent;

/**
 * 
 * @author Jonathan
 */
public class VoiceComponentPanel extends javax.swing.JPanel {

	private Component component;

	private int collapsedHeight;

	private int uncollapsedHeight;

	private boolean first;

	static Logger logger = Logger.getLogger(marytts.tools.newinstall.VoiceComponentPanel.class.getName());

	/**
	 * Creates new form VoiceComponentPanel
	 */
	public VoiceComponentPanel(Component component) {

		this.component = component;
		this.first = true;
		initComponents();
		fillFields(component);
	}

	/* @formatter:off
	
	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
	 * this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        componentNameLabel = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        installButton = new javax.swing.JButton();
        collapseButton = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        localeValueLabel = new javax.swing.JLabel();
        localeLabel = new javax.swing.JLabel();
        genderLabel = new javax.swing.JLabel();
        genderValueLabel = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        typeValueLabel = new javax.swing.JLabel();
        sizeLabel = new javax.swing.JLabel();
        sizeValueLabel = new javax.swing.JLabel();
        collapsiblePanel = new javax.swing.JPanel();
        versionValueLabel = new javax.swing.JLabel();
        licenseLabel = new javax.swing.JLabel();
        licenseValueLabel = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(535, 63));

        componentNameLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        componentNameLabel.setText("jLabel1");

        statusLabel.setText("jLabel2");

        installButton.setText("Install");
        installButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                installButtonActionPerformed(evt);
            }
        });

        collapseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collapseButtonActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        localeValueLabel.setText("jLabel");

        localeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        localeLabel.setText("Locale:");

        genderLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        genderLabel.setText("Gender:");

        genderValueLabel.setText("jLabel6");

        typeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        typeLabel.setText("Type:");

        typeValueLabel.setText("jLabel6");

        sizeLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        sizeLabel.setText("Size:");

        sizeValueLabel.setText("jLabel6");

        collapsiblePanel.setPreferredSize(new java.awt.Dimension(0, 0));

        versionValueLabel.setText("jLabel4");

        licenseLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        licenseLabel.setText("License:");

        licenseValueLabel.setText("jLabel6");

        versionLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        versionLabel.setText("Version:");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Courier New", 0, 10)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Description"));
        jTextArea1.setMaximumSize(new java.awt.Dimension(132, 30));
        jTextArea1.setPreferredSize(new java.awt.Dimension(132, 40));

        javax.swing.GroupLayout collapsiblePanelLayout = new javax.swing.GroupLayout(collapsiblePanel);
        collapsiblePanel.setLayout(collapsiblePanelLayout);
        collapsiblePanelLayout.setHorizontalGroup(
            collapsiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(versionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(versionValueLabel)
                .addGap(115, 115, 115)
                .addComponent(licenseLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(licenseValueLabel)
                .addGap(153, 153, 153))
            .addGroup(collapsiblePanelLayout.createSequentialGroup()
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        collapsiblePanelLayout.setVerticalGroup(
            collapsiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(collapsiblePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(collapsiblePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(licenseLabel)
                    .addComponent(licenseValueLabel)
                    .addComponent(versionLabel)
                    .addComponent(versionValueLabel)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(componentNameLabel)
                            .addComponent(collapseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(localeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(localeValueLabel)
                                .addGap(18, 18, 18)
                                .addComponent(typeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(typeValueLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(genderLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(genderValueLabel)
                                .addGap(18, 18, 18)
                                .addComponent(sizeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sizeValueLabel)))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(installButton)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(statusLabel)
                                .addGap(18, 18, 18))))
                    .addComponent(collapsiblePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(componentNameLabel)
                                .addComponent(statusLabel))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(collapseButton))
                                .addGroup(layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(genderLabel)
                                            .addComponent(genderValueLabel)
                                            .addComponent(sizeLabel)
                                            .addComponent(sizeValueLabel))
                                        .addComponent(installButton)))))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(localeLabel)
                        .addComponent(localeValueLabel)
                        .addComponent(typeLabel)
                        .addComponent(typeValueLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collapsiblePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	/* @formatter:on */
	private void installButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed

	}// GEN-LAST:event_jButton1ActionPerformed

	private void collapseButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jToggleButton1ActionPerformed

		if (this.first) {
			this.collapsedHeight = this.collapseButton.getBounds().y + this.collapseButton.getBounds().height + 4;
			this.uncollapsedHeight = this.collapsiblePanel.getBounds().y + this.collapsiblePanel.getBounds().height + 4;
			this.first = false;
		}

		logger.debug("PS before: " + this.getPreferredSize());
		logger.debug("S before: " + this.getSize());

		if (this.collapseButton.isSelected()) {
			// this.setSize(new Dimension(this.getPreferredSize().width, this.uncollapsedHeight));
			this.setPreferredSize(new Dimension(this.getPreferredSize().width, this.uncollapsedHeight));
		} else {
			// this.setSize(new Dimension(this.getWidth(), this.collapsedHeight));
			this.setPreferredSize(new Dimension(this.getWidth(), this.collapsedHeight));
		}

		logger.debug("PS after: " + this.getPreferredSize());
		logger.debug("S after: " + this.getSize());

		this.revalidate();
		this.repaint();
		this.getParent().getParent().repaint();

	}// GEN-LAST:event_jToggleButton1ActionPerformed

	private void fillFields(Component component) {
		this.componentNameLabel.setText(component.getName());
		this.jTextArea1.setText(component.getDescription().replaceAll("[\\t\\n]", " ").replaceAll("( )+", " "));
		this.versionValueLabel.setText(component.getVersion());
		this.licenseValueLabel.setText(component.getLicenseShortName());
		this.sizeValueLabel.setText(String.valueOf(component.getSize()));
		this.statusLabel.setText(component.getStatus().toString());
		this.localeValueLabel.setText(component.getLocale().toString());

		// TODO solve this in a better way
		if (component instanceof VoiceComponent) {
			this.typeValueLabel.setText(((VoiceComponent) component).getType());
			this.genderValueLabel.setText(((VoiceComponent) component).getGender());
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JToggleButton collapseButton;
	private javax.swing.JPanel collapsiblePanel;
	private javax.swing.JLabel componentNameLabel;
	private javax.swing.JLabel genderLabel;
	private javax.swing.JLabel genderValueLabel;
	private javax.swing.JButton installButton;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JLabel licenseLabel;
	private javax.swing.JLabel licenseValueLabel;
	private javax.swing.JLabel localeLabel;
	private javax.swing.JLabel localeValueLabel;
	private javax.swing.JLabel sizeLabel;
	private javax.swing.JLabel sizeValueLabel;
	private javax.swing.JLabel statusLabel;
	private javax.swing.JLabel typeLabel;
	private javax.swing.JLabel typeValueLabel;
	private javax.swing.JLabel versionLabel;
	private javax.swing.JLabel versionValueLabel;
	// End of variables declaration//GEN-END:variables
}
