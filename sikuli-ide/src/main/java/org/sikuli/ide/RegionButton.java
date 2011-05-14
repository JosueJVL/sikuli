/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.ide;


import java.awt.image.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.*;

import org.sikuli.script.CapturePrompt;
import org.sikuli.script.ScreenImage;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.Observer;
import org.sikuli.script.Subject;
import org.sikuli.script.Debug;

class RegionButton extends JButton implements ActionListener, Observer{
   SikuliPane _pane;
   int _x, _y, _w, _h;


   public RegionButton(SikuliPane pane, int x, int y, int w, int h){
      _pane = pane;
      _x = x;
      _y = y;
      _w = w;
      _h = h;
      setIcon(new ImageIcon(getRegionImage(x,y,w,h)));
      setBorderPainted(true);
      setToolTipText( this.toString() );
      addActionListener(this);
   }

   public String toString(){
      return String.format("Region(%d,%d,%d,%d)", _x, _y, _w, _h);
   }

   public void update(Subject s){
      if(s instanceof CapturePrompt){
         CapturePrompt cp = (CapturePrompt)s;
         ScreenImage r = cp.getSelection();
         if(r!=null){
            cp.close();
            try{
               Thread.sleep(300);
            }
            catch(InterruptedException ie){}
            Rectangle roi = r.getROI();
            _x = (int)roi.getX();
            _y = (int)roi.getY();
            _w = (int)roi.getWidth();
            _h = (int)roi.getHeight();
            BufferedImage img = getRegionImage(_x, _y, _w, _h);
            setIcon(new ImageIcon(img));
            setToolTipText( this.toString() );
         }
      }
      SikuliIDE.getInstance().setVisible(true);
   }

   public void actionPerformed(ActionEvent ae){
      SikuliIDE ide = SikuliIDE.getInstance();
      SikuliPane codePane = ide.getCurrentCodePane();
      ide.setVisible(false);
      CapturePrompt prompt = new CapturePrompt(null, this);
      prompt.prompt(SikuliIDE._I("msgCapturePrompt"), 500);
   }

   private BufferedImage getRegionImage(int x, int y, int w, int h) {
      Region region = new Region(x, y, w, h);
      Screen _screen = region.getScreen();
      ScreenImage simg = _screen.capture();
      int scr_w = _screen.w, scr_h = _screen.h;
      int max_h = 80; // FIXME: put max_h in UserPreferences
      float scale = (float)max_h/scr_h;
      scr_w *= scale;
      scr_h *= scale;
      BufferedImage screen = new BufferedImage(scr_w, scr_h, BufferedImage.TYPE_INT_RGB);
      Graphics2D screen_g2d = screen.createGraphics();
      try {
         screen_g2d.drawImage(simg.getImage(), 0, 0,  scr_w, scr_h, null);
         int sx = (int)((x-_screen.x)*scale), sy = (int)((y-_screen.y)*scale),
             sw = (int)(w*scale), sh = (int)(h*scale);
         screen_g2d.setColor(new Color(255,0,0, 150));
         screen_g2d.fillRect(sx, sy, sw, sh);
      }
      catch (RasterFormatException e) {
         e.printStackTrace();
      }
      screen_g2d.dispose();
      return screen;
   }
}

