package Cat_Saves_MyFeeling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DAO.hirelistDAO;
import DTO.hirelistDTO;

public class Clock extends JPanel{
	Thread th;
	
	JPanel center;
	JScrollPane jsp;
	
	JLabel clock;
	JLabel title;
	JTable result;
	JButton rink;
	
	
	
	public Clock() {
		
		center = new JPanel(new BorderLayout());
		
		setSize(855, 865);
		setLayout(null);
		clock = new JLabel();
		
		Font font = new Font("digital-7", Font.ITALIC | Font.BOLD, 30);
		
		clock.setFont(font);
		
		title = new JLabel("미납 목록");
		ImageIcon img = new ImageIcon("D:/java-study/eclipse-workspace/Project/src//Gallery//전화기.png");
		rink = new JButton(img);
		result = new JTable();
		
		result.getTableHeader().setReorderingAllowed(false); // 이동불가
		result.getTableHeader().setResizingAllowed(false); //크기 조절 불가
		result.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jsp = new JScrollPane(result, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		center.setBounds(118, 150, 615, 500);
		jsp.setBackground(Color.WHITE);
		
		result.setModel(setHireListTable());
		setHireListTableAlignment();	
		
		center.add(jsp);
		add(center);
		
		title.setFont(new Font("궁서체", Font.BOLD, 32));
		title.setBounds(123, 80, 160, 50);
		rink.setBounds(670, 80, 50, 50);
		
		result.setModel(setHireListTable());
		setHireListTableAlignment();
		
		//시계 구현
		ClockRunnable runnable = new ClockRunnable(clock);
		
		th = new Thread(runnable);
		add(title);
		add(rink);
		add(clock);
		
		clock.setBounds(170, 690, 600, 80);
		
		th.start();
		
		rink.addActionListener(new ActionListener(){ 
		  public void actionPerformed(ActionEvent arg0){ 
		    //실행되야할 코드 
			  try {
				Process p = Runtime.getRuntime().exec(
						"C:\\Program Files\\Internet Explorer\\iexplore.exe \"https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%EB%AC%B8%EC%9E%90%EB%B0%9C%EC%86%A1&oquery=%EC%BB%B4%ED%93%A8%ED%84%B0+iexeplorer.exe+%EA%B2%BD%EB%A1%9C+%ED%99%95%EC%9D%B8&tqi=UBpIxlprvTVssipUCuhssssst3l-127656\"");
				Thread.sleep(5000);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "URL을 불러올 수 없습니다.");
			} 
			
		 } 
		});  
		
	}
	public static void main(String[] args) {
		
		JPanel jp = new Clock();
		JFrame jf = new JFrame();
		jf.setBounds(500, 100, 855, 865);
		jf.add(jp);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setResizable(false);
		
	}
	
	public DefaultTableModel setHireListTable() {

		String[] column = {"반납일자", "대출일자","이름", "연락처", "제목", "저자" };
		DefaultTableModel model = new DefaultTableModel(column, 0) {

			public boolean isCellEditable(int rowIndex, int mColInde) {
				return false;
				}
		};
		hirelistDAO hires = hirelistDAO.getInstance();
		ArrayList<hirelistDTO> hire;

		hire = hires.selectOverdue();
		
		System.out.println("overduesize : " + hire.size());
		
		for (hirelistDTO h : hire) {
			Vector<Object> row = new Vector<Object>();
			row.add(h.getIndate().substring(0, 10));
			row.add(h.getOutdate().substring(0, 10));
			row.add(h.getMname());
			row.add(h.getPhone());
			row.add(h.getTitle());
			row.add(h.getAuthor());
			model.addRow(row);
		}
		return model;
	}
	
	public void setHireListTableAlignment() {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		result.setRowHeight(30);
		result.getColumnModel().getColumn(0).setPreferredWidth(80);
		result.getColumnModel().getColumn(1).setPreferredWidth(80);
		result.getColumnModel().getColumn(2).setPreferredWidth(150);
		result.getColumnModel().getColumn(3).setPreferredWidth(100);
		result.getColumnModel().getColumn(4).setPreferredWidth(300);
		result.getColumnModel().getColumn(5).setPreferredWidth(150);
		for (int i = 0; i < result.getColumnCount(); i++) {
			result.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
	}

	
}

class ClockRunnable implements Runnable { 
	  JLabel clockLabel; 
	  
	  
	  private int year, month, date, day, hour, minute, second;
	  private String yoil;
	  
	  public ClockRunnable(JLabel clockLabel) { 
	    this.clockLabel = clockLabel; 
	 } 
	 @Override 
	 public void run() { 
	   // while 루프의 시작을 여기로 옮겨야 합니다. 
	   while (true) { 
		   Calendar cal = Calendar.getInstance();
		   setCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.DAY_OF_WEEK), 
					cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		   if(hour >= 10 && minute >= 10 && second < 10) {
			   clockLabel.setText(year + " 년  " + month + " 월  " + date + " 일 " + yoil + "  " + 
					      hour + " : " + minute + " : " + "0" + second);
		   } else if(hour >= 10 && minute < 10 && second >= 10) {
			   clockLabel.setText(year + " 년  " + month + " 월  " + date + " 일 " + yoil + "  " + 
					      hour + " : " + 0 + minute + " : " + second);
		   } else if(hour < 10 && minute >= 10 && second >= 10) {
			   clockLabel.setText(year + " 년  " + month + " 월  " + date + " 일 " + yoil + "  " + 
					      0 + hour + " : " + minute + " : " + second);
		   } else if(hour < 10 && minute < 10 && second >= 10) {
			   clockLabel.setText(year + " 년  " + month + " 월  " + date + " 일 " + yoil + "  " + 
					      0 + hour + " : " + 0 + minute + " : " + second);
		   } else if(hour < 10 && minute >= 10 && second < 10) {
			   clockLabel.setText(year + " 년  " + month + " 월  " + date + " 일 " + yoil + "  " + 
					      0 + hour + " : " + minute + " : " + 0 + second);
		   } else if(hour >= 10 &&minute < 10 && second < 10) {
			   clockLabel.setText(year + " 년  " + month + " 월  " + date + " 일 " + yoil + "  " + 
					       hour + " : " + 0 + minute + " : " + 0 + second);
		   } else {
			clockLabel.setText(year + " 년  " + month + " 월  " + date + " 일 " + yoil + "  " + 
					      hour + " : " + minute + " : " + second);
		   }
	     try { 
	       Thread.sleep(1000); 
	       } catch (InterruptedException e) { 
	       return; 
	       } 
	  } 
	}
	 
	 public void setCalendar(int year, int month, int date, int day, int hour, int minute, int second) {
			
			this.year = year; this.month = month + 1; this.date = date; this.day = day;
			this.hour = hour; this.minute = minute; this.second = second;
			switch(this.day) {
				case 1 : yoil = "일요일";
						 break;
				case 2 : yoil = "월요일";
						 break;
				case 3 : yoil = "화요일";
				         break;
				case 4 : yoil = "수요일";
						 break;
				case 5 : yoil = "목요일";
						 break;
				case 6 : yoil = "금요일";
						 break;
				case 7 : yoil = "토요일";
						 break;
			}
			
		}
}


