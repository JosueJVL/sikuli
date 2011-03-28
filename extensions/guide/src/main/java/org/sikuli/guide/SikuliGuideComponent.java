/**
 * 
 */
package org.sikuli.guide;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;

import org.sikuli.script.Debug;
import org.sikuli.script.Region;

public class SikuliGuideComponent extends JComponent{

 
   Region reference_region = null;
   int reference_side = -1;

//   ArrayList<SikuliGuideAnimator> anims = new ArrayList<SikuliGuideAnimator>();
   
   public void startAnimation(){          
      // TODO: this is ugly
      // use animation queue..
      
      if (entrance_anim != null){
         //if (entrance_anim.isRunning()){
            entrance_anim.stop();
         //}
         
         if (!entrance_anim.isPlayed()){
            Debug.log("entrance anim started");
            Debug.log("entrance anim:" + entrance_anim);
            entrance_anim.start();
         }
      } 
      
      if (emphasis_anim != null){     
         
         if (entrance_anim != null)
            entrance_anim.stop();

         Debug.log("emphasis anim started");
         emphasis_anim.start();
      }
   }
   
   public void stopAnimation() {
      if (emphasis_anim != null){     
         emphasis_anim.stop();
      }
      if (entrance_anim != null){
         entrance_anim.stop();
      }
   }
   
   public SikuliGuideAnimator createSlidingAnimator(int offset_x, int offset_y){  
      Point dest = getLocation();
      Point src = new Point(dest.x + offset_x, dest.y + offset_y);
      return new MoveAnimator(this, src, dest);
   }

   public SikuliGuideAnimator createMoveAnimator(int dest_x, int dest_y){
      Point src = getLocation();
      Point dest = new Point(dest_x, dest_y);
      return new MoveAnimator(this, src, dest);
   }

   public SikuliGuideAnimator createCirclingAnimator(int radius) {      
      return new CircleAnimator(this, radius);
   }
   
   
   SikuliGuideAnimator entrance_anim;
   SikuliGuideAnimator emphasis_anim;
   
   public void setEntranceAnimation(SikuliGuideAnimator anim){
      if (entrance_anim != null)
         entrance_anim.stop();
      else
         entrance_anim = anim;
   } 

   public void setEmphasisAnimation(SikuliGuideAnimator anim){
      if (emphasis_anim != null)
         emphasis_anim.stop();

      if (entrance_anim != null)
         entrance_anim.stop();
      
      emphasis_anim = anim;
   } 
   
   public final static int TOP = 0;
   public final static int LEFT = 1;
   public final static int RIGHT = 2;
   public final static int BOTTOM = 3;
   public final static int INSIDE = 4;

   public SikuliGuideComponent(){    
      super();
   }
   

   public void setLocationRelativeTo(JComponent comp, int side) {
      Region region = new Region(comp.getBounds());
      setLocationRelativeToRegion(region, side);
   }

   
   public void setLocationRelativeToRegion(Region region, int side) {
      reference_region = region;
      reference_side = side;
      
      int height = getHeight();
      int width = getWidth();
      if (side == TOP){
         setBounds(region.x + region.w/2 - width/2, region.y - height, width, height);
      } else if (side == BOTTOM){
         setBounds(region.x + region.w/2 - width/2, region.y + region.h, width, height);         
      } else if (side == LEFT){
         setBounds(region.x - width, region.y + region.h/2 - height/2, width, height);                  
      } else if (side == RIGHT){
         setBounds(region.x + region.w, region.y + region.h/2 - height/2, width, height);                  
      } else if (side == INSIDE){
         setBounds(region.x + region.w/2 - width/2, region.y + region.h/2 - height/2, width, height);                  
      }
   }

   public void setHorizontalAlignmentWithRegion(Region region, float f){
      reference_region = region;

      int x0 = region.x;
      int x1 = region.x + region.w - getWidth();

      int x = (int) (x0 + (x1-x0)*f);

      setLocation(x,getY());
   }
   
   public void setVerticalAlignmentWithRegion(Region region, float f){
      reference_region = region;

      int y0 = region.y;
      int y1 = region.y + region.h - getHeight();

      int y = (int) (y0 + (y1-y0)*f);

      setLocation(getX(),y);
   }

   SikuliGuideComponent shadow = null;
   public void setShadow(SikuliGuideComponent shadow){
      this.shadow = shadow;
   }
   
   @Override
   public void setVisible(boolean visible){
      if (shadow != null){
         shadow.setVisible(visible);
      }
      super.setVisible(visible);
   }
   
   @Override
   public void setLocation(int x, int y){
      Point loc = getLocation();
      if (shadow != null){
         Point shadow_loc = shadow.getLocation();
         int dx = x - loc.x;
         int dy = y - loc.y;
         shadow_loc.x += dx;
         shadow_loc.y += dy;
         shadow.setLocation(shadow_loc);
      }
      super.setLocation(x,y);
   }

}