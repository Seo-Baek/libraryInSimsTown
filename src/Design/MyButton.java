package Design;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;


public class MyButton extends JButton {
	

        private Color hoverBackgroundColor;
        private Color pressedBackgroundColor;

        public MyButton() {
            this(null);
        }

        public MyButton(String text) {
            super(text);
            super.setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(pressedBackgroundColor);
            } else if (getModel().isRollover()) {
                g.setColor(hoverBackgroundColor);
            } else {
                g.setColor(getBackground());
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }

        public Color getHoverBackgroundColor() {
            return hoverBackgroundColor;
        }

        public void setHoverBackgroundColor(Color hoverBackgroundColor) {
            this.hoverBackgroundColor = hoverBackgroundColor;
        }

        public Color getPressedBackgroundColor() {
            return pressedBackgroundColor;
        }

        public void setPressedBackgroundColor(Color pressedBackgroundColor) {
            this.pressedBackgroundColor = pressedBackgroundColor;
        }
    
        
        
        public void paint(Graphics g)
        {
        Graphics2D g2 = (Graphics2D) g;
         
        //그라데이션 시작이 100,100이고 300,300 인 곳은 파란색으로 하는 100-> 300 인 그라데이션 칠해라.
        //GradientPaint gp = new GradientPaint(100, 100, Color.RED, 300, 300, Color.BLUE);  
        GradientPaint gp = new GradientPaint(100, 100, Color.LIGHT_GRAY, 100, 200, Color.GRAY);
         
        g2.setColor(Color.YELLOW);  // 붓에 노란색 칠함.
        g2.fillRect(150, 150, 100, 100);  // 노란색칠해진 붓으로 사각형 그리고 채워라.
         
        g2.setPaint(gp);  // 그라데이션
        g2.fillRoundRect(50, 50, 250, 250, 50, 50);
         
        }// paint Method


}
