/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.ide;

import java.awt.*;
import java.awt.event.*;
import java.util.prefs.*;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import org.jdesktop.layout.*;

import org.sikuli.script.Debug;

/*
 * Created by JFormDesigner on Mon Nov 16 10:13:52 EST 2009
 */


public class PreferencesWin extends JFrame {
   UserPreferences pref = UserPreferences.getInstance();
   int _cap_hkey, _cap_mod;
   int _old_cap_hkey, _old_cap_mod;

   public PreferencesWin() {
      super( SikuliIDE._I("winPreferences") );
      initComponents();
      loadPrefs();
   }

   private void loadPrefs(){
      SikuliIDE ide = SikuliIDE.getInstance();
      double delay = pref.getCaptureDelay();
      _spnDelay.setValue(delay);
      _old_cap_hkey = _cap_hkey = pref.getCaptureHotkey();
      _old_cap_mod = _cap_mod = pref.getCaptureHotkeyModifiers();
      setTxtHotkey(_cap_hkey, _cap_mod);
      switch(pref.getAutoNamingMethod()){
         case UserPreferences.AUTO_NAMING_TIMESTAMP:
            _radTimestamp.setSelected(true);
            break;
         case UserPreferences.AUTO_NAMING_OCR:
            _radOCR.setSelected(true);
            break;
         case UserPreferences.AUTO_NAMING_OFF:
            _radOff.setSelected(true);
            break;
         default:
            Debug.error("Error in reading auto naming method preferences");
      }
      _chkAutoUpdate.setSelected(pref.getCheckUpdate());

      _chkExpandTab.setSelected(pref.getExpandTab());
      _spnTabWidth.setValue(pref.getTabWidth());
      initFontPrefs();
   }
   
   private void initFontPrefs(){
      UserPreferences pref = UserPreferences.getInstance();
      String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment()
                              .getAvailableFontFamilyNames();
      for (String font: fontList)
         _cmbFontName.addItem(font);
      _cmbFontName.setSelectedItem(pref.getFontName());
      _spnFontSize.setValue(pref.getFontSize());
   }

   private void savePrefs(){
      SikuliIDE ide = SikuliIDE.getInstance();
      pref.setCaptureDelay((Double)_spnDelay.getValue());
      pref.setCaptureHotkey(_cap_hkey);
      pref.setCaptureHotkeyModifiers(_cap_mod);
      pref.setAutoNamingMethod(
            _radTimestamp.isSelected()?UserPreferences.AUTO_NAMING_TIMESTAMP:
            _radOCR.isSelected()?UserPreferences.AUTO_NAMING_OCR:
            UserPreferences.AUTO_NAMING_OFF);
      if(_old_cap_hkey != _cap_hkey || _old_cap_mod != _cap_mod){
         //FIXME: remove the old hotkey
         ide.installCaptureHotkey(_cap_hkey, _cap_mod);
      }
      pref.setCheckUpdate(_chkAutoUpdate.isSelected());

      pref.setExpandTab(_chkExpandTab.isSelected());
      pref.setTabWidth((Integer)_spnTabWidth.getValue());

      pref.setFontName( (String)_cmbFontName.getSelectedItem() );
      pref.setFontSize( (Integer)_spnFontSize.getValue() );
   }

   private void setTxtHotkey(int code, int mod){
      _cap_hkey = code;
      _cap_mod = mod;
      _txtHotkey.setText( Utils.convertKeyToText(code, mod) );
   }

   private void btnOkActionPerformed(ActionEvent e) {
      savePrefs();
      this.dispose();
   }

   private void btnCancelActionPerformed(ActionEvent e) {
      this.dispose();
   }

   private void txtHotkeyFocusGained(FocusEvent e) {
      _txtHotkey.setEditable(true);
   }

   private void txtHotkeyKeyPressed(KeyEvent e) {
      int code = e.getKeyCode();
      int mod = e.getModifiers();
      Debug.log(7, "" + code + " " + mod);
      setTxtHotkey(code, mod);
      _txtHotkey.setEditable(false);
   }

   private void updateFontPreview(){
      SikuliIDE ide = SikuliIDE.getInstance();
      Font font = new Font((String)_cmbFontName.getSelectedItem(), Font.PLAIN, 
                           (Integer)_spnFontSize.getValue());
      ide.getCurrentCodePane().setFont(font);
   }

   private void fontNameItemStateChanged(ItemEvent e) {
      updateFontPreview();
   }

   private void fontSizeStateChanged(ChangeEvent e) {
      updateFontPreview();
   }

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
      DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
      _tabPane = new JTabbedPane();
      JPanel paneCapture = new JPanel();
      _txtHotkey = new JTextField();
      _lblHotkey = new JLabel();
      _lblDelay = new JLabel();
      _spnDelay = new JSpinner();
      _lblDelaySecs = new JLabel();
      _lblNaming = new JLabel();
      _radTimestamp = new JRadioButton();
      _radOCR = new JRadioButton();
      _radOff = new JRadioButton();
      _paneTextEditing = new JPanel();
      _chkExpandTab = new JCheckBox();
      _lblTabWidth = new JLabel();
      _cmbFontName = new JComboBox();
      _lblFont = new JLabel();
      _titleAppearance = compFactory.createTitle("");
      _titleIndentation = compFactory.createTitle("");
      _spnTabWidth = new JSpinner();
      _lblFontSize = new JLabel();
      _spnFontSize = new JSpinner();
      JPanel paneGeneral = new JPanel();
      _chkAutoUpdate = new JCheckBox();
      JPanel paneOkCancel = new JPanel();
      JPanel hSpacer1 = new JPanel(null);
      _btnOk = new JButton();
      _btnCancel = new JButton();

      //======== this ========
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

      //======== _tabPane ========
      {
         _tabPane.setBorder(new EmptyBorder(10, 10, 0, 10));

         //======== paneCapture ========
         {

            //---- _txtHotkey ----
            _txtHotkey.setHorizontalAlignment(SwingConstants.RIGHT);
            _txtHotkey.addFocusListener(new FocusAdapter() {
               @Override
               public void focusGained(FocusEvent e) {
                  txtHotkeyFocusGained(e);
               }
            });
            _txtHotkey.addKeyListener(new KeyAdapter() {
               @Override
               public void keyPressed(KeyEvent e) {
                  txtHotkeyKeyPressed(e);
               }
            });

            //---- _lblHotkey ----
            _lblHotkey.setLabelFor(_txtHotkey);

            //---- _lblDelay ----
            _lblDelay.setLabelFor(_spnDelay);

            //---- _spnDelay ----
            _spnDelay.setModel(new SpinnerNumberModel(1.0, 0.0, null, 0.1));

            //---- _radTimestamp ----
            _radTimestamp.setSelected(true);

            GroupLayout paneCaptureLayout = new GroupLayout(paneCapture);
            paneCapture.setLayout(paneCaptureLayout);
            paneCaptureLayout.setHorizontalGroup(
               paneCaptureLayout.createParallelGroup()
                  .add(paneCaptureLayout.createSequentialGroup()
                     .add(26, 26, 26)
                     .add(paneCaptureLayout.createParallelGroup()
                        .add(GroupLayout.TRAILING, _lblDelay)
                        .add(GroupLayout.TRAILING, _lblHotkey)
                        .add(GroupLayout.TRAILING, _lblNaming))
                     .addPreferredGap(LayoutStyle.RELATED)
                     .add(paneCaptureLayout.createParallelGroup()
                        .add(_radTimestamp)
                        .add(_radOCR)
                        .add(_radOff)
                        .add(paneCaptureLayout.createSequentialGroup()
                           .add(_spnDelay, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                           .addPreferredGap(LayoutStyle.RELATED)
                           .add(_lblDelaySecs, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                        .add(_txtHotkey, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                     .add(69, 69, 69))
            );
            paneCaptureLayout.setVerticalGroup(
               paneCaptureLayout.createParallelGroup()
                  .add(paneCaptureLayout.createSequentialGroup()
                     .add(34, 34, 34)
                     .add(paneCaptureLayout.createParallelGroup(GroupLayout.BASELINE)
                        .add(_lblHotkey, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                        .add(_txtHotkey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                     .addPreferredGap(LayoutStyle.RELATED)
                     .add(paneCaptureLayout.createParallelGroup()
                        .add(_lblDelay, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                        .add(_spnDelay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .add(_lblDelaySecs, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                     .addPreferredGap(LayoutStyle.RELATED)
                     .add(paneCaptureLayout.createParallelGroup(GroupLayout.LEADING, false)
                        .add(paneCaptureLayout.createSequentialGroup()
                           .add(paneCaptureLayout.createParallelGroup(GroupLayout.BASELINE)
                              .add(_lblNaming, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                              .add(_radTimestamp))
                           .add(18, 18, 18)
                           .add(_radOff)
                           .addPreferredGap(LayoutStyle.RELATED))
                        .add(GroupLayout.TRAILING, paneCaptureLayout.createSequentialGroup()
                           .add(_radOCR)
                           .add(21, 21, 21)))
                     .add(80, 80, 80))
            );
         }
         _tabPane.addTab(I18N._I("prefTabScreenCapturing"), paneCapture);


         //======== _paneTextEditing ========
         {

            //---- _lblTabWidth ----
            _lblTabWidth.setLabelFor(_spnTabWidth);

            //---- _cmbFontName ----
            _cmbFontName.addItemListener(new ItemListener() {
               public void itemStateChanged(ItemEvent e) {
                  fontNameItemStateChanged(e);
               }
            });

            //---- _lblFont ----
            _lblFont.setLabelFor(_cmbFontName);

            //---- _lblFontSize ----
            _lblFontSize.setLabelFor(_cmbFontName);

            //---- _spnFontSize ----
            _spnFontSize.addChangeListener(new ChangeListener() {
               public void stateChanged(ChangeEvent e) {
                  fontSizeStateChanged(e);
               }
            });

            GroupLayout _paneTextEditingLayout = new GroupLayout(_paneTextEditing);
            _paneTextEditing.setLayout(_paneTextEditingLayout);
            _paneTextEditingLayout.setHorizontalGroup(
               _paneTextEditingLayout.createParallelGroup()
                  .add(_paneTextEditingLayout.createSequentialGroup()
                     .add(48, 48, 48)
                     .add(_paneTextEditingLayout.createParallelGroup()
                        .add(_titleIndentation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .add(_paneTextEditingLayout.createSequentialGroup()
                           .add(_titleAppearance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                           .addPreferredGap(LayoutStyle.RELATED)
                           .add(_paneTextEditingLayout.createParallelGroup()
                              .add(_paneTextEditingLayout.createSequentialGroup()
                                 .add(29, 29, 29)
                                 .add(_paneTextEditingLayout.createParallelGroup()
                                    .add(GroupLayout.TRAILING, _lblTabWidth)
                                    .add(GroupLayout.TRAILING, _lblFont)
                                    .add(GroupLayout.TRAILING, _lblFontSize))
                                 .addPreferredGap(LayoutStyle.RELATED)
                                 .add(_paneTextEditingLayout.createParallelGroup()
                                    .add(_cmbFontName, 0, 177, Short.MAX_VALUE)
                                    .add(_spnFontSize, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                                    .add(_spnTabWidth, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
                                 .addPreferredGap(LayoutStyle.RELATED, 55, Short.MAX_VALUE))
                              .add(_chkExpandTab, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))))
                     .addContainerGap())
            );
            _paneTextEditingLayout.setVerticalGroup(
               _paneTextEditingLayout.createParallelGroup()
                  .add(_paneTextEditingLayout.createSequentialGroup()
                     .add(21, 21, 21)
                     .add(_titleIndentation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                     .add(_paneTextEditingLayout.createParallelGroup()
                        .add(_paneTextEditingLayout.createSequentialGroup()
                           .add(81, 81, 81)
                           .add(_titleAppearance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .add(_paneTextEditingLayout.createSequentialGroup()
                           .addPreferredGap(LayoutStyle.RELATED)
                           .add(_chkExpandTab)
                           .addPreferredGap(LayoutStyle.RELATED)
                           .add(_paneTextEditingLayout.createParallelGroup()
                              .add(_lblTabWidth, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                              .add(_spnTabWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                           .add(40, 40, 40)
                           .add(_paneTextEditingLayout.createParallelGroup(GroupLayout.BASELINE)
                              .add(_lblFont)
                              .add(_cmbFontName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                           .addPreferredGap(LayoutStyle.RELATED)
                           .add(_paneTextEditingLayout.createParallelGroup(GroupLayout.TRAILING)
                              .add(_lblFontSize, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                              .add(_spnFontSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                     .addContainerGap(55, Short.MAX_VALUE))
            );
            _paneTextEditingLayout.linkSize(new Component[] {_lblTabWidth, _spnTabWidth}, GroupLayout.VERTICAL);
            _paneTextEditingLayout.linkSize(new Component[] {_cmbFontName, _lblFont}, GroupLayout.VERTICAL);
         }
         _tabPane.addTab(I18N._I("PreferencesWin.paneTextEditing.tab.title"), _paneTextEditing);


         //======== paneGeneral ========
         {

            GroupLayout paneGeneralLayout = new GroupLayout(paneGeneral);
            paneGeneral.setLayout(paneGeneralLayout);
            paneGeneralLayout.setHorizontalGroup(
               paneGeneralLayout.createParallelGroup()
                  .add(GroupLayout.TRAILING, paneGeneralLayout.createSequentialGroup()
                     .add(85, 85, 85)
                     .add(_chkAutoUpdate, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                     .add(82, 82, 82))
            );
            paneGeneralLayout.setVerticalGroup(
               paneGeneralLayout.createParallelGroup()
                  .add(paneGeneralLayout.createSequentialGroup()
                     .add(32, 32, 32)
                     .add(_chkAutoUpdate)
                     .addContainerGap(201, Short.MAX_VALUE))
            );
         }
         _tabPane.addTab(I18N._I("prefTabGeneralSettings"), paneGeneral);

      }
      contentPane.add(_tabPane, BorderLayout.CENTER);

      //======== paneOkCancel ========
      {
         paneOkCancel.setBorder(new EmptyBorder(5, 5, 5, 5));
         paneOkCancel.setLayout(new BoxLayout(paneOkCancel, BoxLayout.X_AXIS));
         paneOkCancel.add(hSpacer1);

         //---- _btnOk ----
         _btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               btnOkActionPerformed(e);
            }
         });
         paneOkCancel.add(_btnOk);

         //---- _btnCancel ----
         _btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               btnCancelActionPerformed(e);
            }
         });
         paneOkCancel.add(_btnCancel);
      }
      contentPane.add(paneOkCancel, BorderLayout.SOUTH);
      setSize(470, 375);
      setLocationRelativeTo(getOwner());

      //---- btngrpNaming ----
      ButtonGroup btngrpNaming = new ButtonGroup();
      btngrpNaming.add(_radTimestamp);
      btngrpNaming.add(_radOCR);
      btngrpNaming.add(_radOff);

      initComponentsI18n();
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

   private void initComponentsI18n() {
      // JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
      DefaultComponentFactory.setTextAndMnemonic(_titleAppearance, I18N._I("PreferencesWin.titleAppearance.textWithMnemonic"));
      DefaultComponentFactory.setTextAndMnemonic(_titleIndentation, I18N._I("PreferencesWin.titleIndentation.textWithMnemonic"));
      _lblHotkey.setText(I18N._I("prefCaptureHotkey"));
      _lblDelay.setText(I18N._I("prefCaptureDelay"));
      _lblDelaySecs.setText(I18N._I("prefSeconds"));
      _lblNaming.setText(I18N._I("prefAutoNaming"));
      _radTimestamp.setText(I18N._I("prefTimestamp"));
      _radOCR.setText(I18N._I("prefRecognizedText"));
      _radOff.setText(I18N._I("prefManualInput"));
      _tabPane.setTitleAt(0, I18N._I("prefTabScreenCapturing"));
      _chkExpandTab.setText(I18N._I("PreferencesWin.chkExpandTab.text"));
      _lblTabWidth.setText(I18N._I("PreferencesWin.lblTabWidth.text"));
      _lblFont.setText(I18N._I("PreferencesWin.lblFont.text"));
      _lblFontSize.setText(I18N._I("PreferencesWin.lblFontSize.text"));
      _tabPane.setTitleAt(1, I18N._I("PreferencesWin.paneTextEditing.tab.title"));
      _chkAutoUpdate.setText(I18N._I("prefGeneralAutoCheck"));
      _tabPane.setTitleAt(2, I18N._I("prefTabGeneralSettings"));
      _btnOk.setText(I18N._I("ok"));
      _btnCancel.setText(I18N._I("cancel"));
      // JFormDesigner - End of component i18n initialization  //GEN-END:initI18n
   }

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
   private JTabbedPane _tabPane;
   private JTextField _txtHotkey;
   private JLabel _lblHotkey;
   private JLabel _lblDelay;
   private JSpinner _spnDelay;
   private JLabel _lblDelaySecs;
   private JLabel _lblNaming;
   private JRadioButton _radTimestamp;
   private JRadioButton _radOCR;
   private JRadioButton _radOff;
   private JPanel _paneTextEditing;
   private JCheckBox _chkExpandTab;
   private JLabel _lblTabWidth;
   private JComboBox _cmbFontName;
   private JLabel _lblFont;
   private JLabel _titleAppearance;
   private JLabel _titleIndentation;
   private JSpinner _spnTabWidth;
   private JLabel _lblFontSize;
   private JSpinner _spnFontSize;
   private JCheckBox _chkAutoUpdate;
   private JButton _btnOk;
   private JButton _btnCancel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
