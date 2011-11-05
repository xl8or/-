package android.animation;

import android.animation.TimeInterpolator;
import java.util.ArrayList;

public abstract class Animator implements Cloneable {

   ArrayList<Animator.AnimatorListener> mListeners = null;


   public Animator() {}

   public void addListener(Animator.AnimatorListener var1) {
      if(this.mListeners == null) {
         ArrayList var2 = new ArrayList();
         this.mListeners = var2;
      }

      this.mListeners.add(var1);
   }

   public void cancel() {}

   public Animator clone() {
      // $FF: Couldn't be decompiled
   }

   public void end() {}

   public abstract long getDuration();

   public ArrayList<Animator.AnimatorListener> getListeners() {
      return this.mListeners;
   }

   public abstract long getStartDelay();

   public abstract boolean isRunning();

   public void removeAllListeners() {
      if(this.mListeners != null) {
         this.mListeners.clear();
         this.mListeners = null;
      }
   }

   public void removeListener(Animator.AnimatorListener var1) {
      if(this.mListeners != null) {
         this.mListeners.remove(var1);
         if(this.mListeners.size() == 0) {
            this.mListeners = null;
         }
      }
   }

   public abstract Animator setDuration(long var1);

   public abstract void setInterpolator(TimeInterpolator var1);

   public abstract void setStartDelay(long var1);

   public void setTarget(Object var1) {}

   public void setupEndValues() {}

   public void setupStartValues() {}

   public void start() {}

   public interface AnimatorListener {

      void onAnimationCancel(Animator var1);

      void onAnimationEnd(Animator var1);

      void onAnimationRepeat(Animator var1);

      void onAnimationStart(Animator var1);
   }
}
