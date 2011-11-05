package android.animation;

import android.animation.Animator;

public abstract class AnimatorListenerAdapter implements Animator.AnimatorListener {

   public AnimatorListenerAdapter() {}

   public void onAnimationCancel(Animator var1) {}

   public void onAnimationEnd(Animator var1) {}

   public void onAnimationRepeat(Animator var1) {}

   public void onAnimationStart(Animator var1) {}
}
