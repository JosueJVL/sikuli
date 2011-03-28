/**
 * 
 */
package org.sikuli.guide;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import org.sikuli.script.Region;

public class SikuliGuideCircle extends SikuliGuideComponent{

   Region region;
   public SikuliGuideCircle(Region region){         
      super();
      this.region = region;
      
      Rectangle rect = region.getRect();
      rect.grow(3,3);
      setBounds(rect);
      setForeground(Color.red);
   }

   public void paint(Graphics g){
      super.paint(g);
      Graphics2D g2d = (Graphics2D)g;
      
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);       
      
      Stroke pen = new BasicStroke(3.0F);    
      g2d.setStroke(pen);

      Rectangle r = region.getRect();      
      
      g2d.translate(3, 3);
      Ellipse2D.Double ellipse =
         new Ellipse2D.Double(0,0,r.width-1,r.height-1);
      g2d.draw(ellipse);
   } 

}