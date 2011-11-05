package org.apache.harmony.awt.datatransfer;

import java.awt.Point;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;

public class DragSourceEventProxy implements Runnable {

   public static final int DRAG_ACTION_CHANGED = 3;
   public static final int DRAG_DROP_END = 6;
   public static final int DRAG_ENTER = 1;
   public static final int DRAG_EXIT = 5;
   public static final int DRAG_MOUSE_MOVED = 4;
   public static final int DRAG_OVER = 2;
   private final DragSourceContext context;
   private final int modifiers;
   private final boolean success;
   private final int targetActions;
   private final int type;
   private final int userAction;
   private final int x;
   private final int y;


   public DragSourceEventProxy(DragSourceContext var1, int var2, int var3, int var4, Point var5, int var6) {
      this.context = var1;
      this.type = var2;
      this.userAction = var3;
      this.targetActions = var4;
      int var7 = var5.x;
      this.x = var7;
      int var8 = var5.y;
      this.y = var8;
      this.modifiers = var6;
      this.success = (boolean)0;
   }

   public DragSourceEventProxy(DragSourceContext var1, int var2, int var3, boolean var4, Point var5, int var6) {
      this.context = var1;
      this.type = var2;
      this.userAction = var3;
      this.targetActions = var3;
      int var7 = var5.x;
      this.x = var7;
      int var8 = var5.y;
      this.y = var8;
      this.modifiers = var6;
      this.success = var4;
   }

   private DragSourceDragEvent newDragSourceDragEvent() {
      DragSourceContext var1 = this.context;
      int var2 = this.userAction;
      int var3 = this.targetActions;
      int var4 = this.modifiers;
      int var5 = this.x;
      int var6 = this.y;
      return new DragSourceDragEvent(var1, var2, var3, var4, var5, var6);
   }

   public void run() {
      switch(this.type) {
      case 1:
         DragSourceContext var1 = this.context;
         DragSourceDragEvent var2 = this.newDragSourceDragEvent();
         var1.dragEnter(var2);
         return;
      case 2:
         DragSourceContext var3 = this.context;
         DragSourceDragEvent var4 = this.newDragSourceDragEvent();
         var3.dragOver(var4);
         return;
      case 3:
         DragSourceContext var5 = this.context;
         DragSourceDragEvent var6 = this.newDragSourceDragEvent();
         var5.dropActionChanged(var6);
         return;
      case 4:
         DragSourceContext var7 = this.context;
         DragSourceDragEvent var8 = this.newDragSourceDragEvent();
         var7.dragMouseMoved(var8);
         return;
      case 5:
         DragSourceContext var9 = this.context;
         DragSourceContext var10 = this.context;
         int var11 = this.x;
         int var12 = this.y;
         DragSourceEvent var13 = new DragSourceEvent(var10, var11, var12);
         var9.dragExit(var13);
         return;
      case 6:
         DragSourceContext var14 = this.context;
         DragSourceContext var15 = this.context;
         int var16 = this.userAction;
         boolean var17 = this.success;
         int var18 = this.x;
         int var19 = this.y;
         DragSourceDropEvent var20 = new DragSourceDropEvent(var15, var16, var17, var18, var19);
         var14.dragExit(var20);
         return;
      default:
      }
   }
}
