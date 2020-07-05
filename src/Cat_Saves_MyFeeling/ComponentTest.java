package Cat_Saves_MyFeeling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DAO.booklistDAO;
import DAO.hirelistDAO;
import DAO.memberDAO;
import DTO.booklistDTO;
import DTO.hirelistDTO;
import DTO.memberDTO;

@SuppressWarnings("serial")
public class ComponentTest extends JPanel {

	JPanel left;
	JPanel right;
	JPanel center;
	JScrollPane jsp;

	JLabel title;
	JButton refresh;
	JTable result;
	JTable sumT;

	JComboBox<String> reqC;

	JTextField reqT;
	JButton search;
	JButton all;
	JButton rent;

	DefaultTableModel model;

	public static void main(String[] args) {

		JPanel left = new ComponentTest();
		JPanel right = new ComponentTest();
  
		JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, left, right);
		jsp.setResizeWeight(0.5);
		jsp.setEnabled(false);
		jsp.setDividerSize(10);
		jsp.setOneTouchExpandable(true);

//		String[] hireReq = {"이름", "연락처"};
//		String[] bookReq = {"제목", "지은이"};
		String[] hiretitle = { "이름", "연락처", "제목", "저자", "대출일자", "반납일자" };
		String[] booktitle = { "일련번호", "제목", "지은이", "위치", "대여여부" };
//		
//		DefaultTableModel hire = new DefaultTableModel(hiretitle,0);
//		DefaultTableModel book = new DefaultTableModel(booktitle,0);
//		
//		((ComponentTest) left).setTitle("대여 목록", hireReq, hire, "대출");
//		((ComponentTest) right).setTitle("도서 목록", bookReq, book, "반납");

		JFrame jf = new JFrame();

		jf.add(jsp);
		jf.setBounds(0, 0, 1720, 900);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public ComponentTest() {

		center = new JPanel(new BorderLayout());

		title = new JLabel();
		ImageIcon img = new ImageIcon("D:/java-study/eclipse-workspace/Project/src/Gallery/refreshButton.png");
		refresh = new JButton(img);

		reqT = new JTextField(100);
		search = new JButton("검색");
		all = new JButton("전체조회");
		rent = new JButton();

		title.setBounds(123, 80, 160, 50);
		refresh.setBounds(670, 80, 50, 50);
		reqT.setBounds(235, 740, 291, 30);

		add(title);
		add(refresh);
		add(reqT);
		add(search);
		add(all);
		add(rent);

		setLayout(null);

		title.setFont(new Font("궁서체", Font.BOLD, 32));
		refresh.setFont(new Font("궁서체", Font.BOLD, 14));
		reqT.setFont(new Font("궁서체", Font.BOLD, 14));
		search.setFont(new Font("궁서체", Font.BOLD, 14));
		all.setFont(new Font("궁서체", Font.BOLD, 14));
		rent.setFont(new Font("궁서체", Font.BOLD, 14));

	}

	public void setTitle(String title, String[] req, String rent, int height) {

		this.title.setText(title);
		reqC = new JComboBox<String>(req);
		reqC.setBounds(130, 740, 100, 30);
		add(reqC);
		reqC.setFont(new Font("궁서체", Font.BOLD, 14));
		result = new JTable();
		result.getTableHeader().setReorderingAllowed(false); // 이동불가
		result.getTableHeader().setResizingAllowed(false); //크기 조절 불가
		result.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jsp = new JScrollPane(result, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		center.setBounds(118, 150, 615, height);
		jsp.setBackground(Color.WHITE);
		center.add(jsp);
		add(center);
		this.rent.setText(rent);

	}

	public void addTable(JPanel jp) {
		String[] sumtitle = { "전체 권수", "보유 권수" };
		DefaultTableModel sum = new DefaultTableModel(sumtitle, 0) {
			@Override
			public boolean isCellEditable(int rowIndex, int mColInde) {
				return false;
				}
		};	
		sumT = new JTable(sum);
		JScrollPane jsp = new JScrollPane(sumT, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jp.add(jsp);
		jsp.setBounds(118, 650, 615, 55);
	}

	public void set3ButtonLoc() {
		search.setBounds(531, 722, 95, 35);
		all.setBounds(531, 762, 95, 35);
		rent.setBounds(633, 722, 88, 75);
	}

	public void set2Buttonloc() {
		search.setBounds(531, 740, 95, 35);
		all.setBounds(631, 740, 95, 35);
	}
	
	public DefaultTableModel setCountTable(String comboText, String keyword) {

		String[] column = { "전체 권수", "보유 권수" };
		DefaultTableModel model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int rowIndex, int mColInde) {
				return false;
				}
		};	
		hirelistDAO counts = hirelistDAO.getInstance();
		Integer[] count;
			
			if (keyword == null) {
				count = counts.countBook("", "");
			} else {
				count = counts.countBook(comboText, keyword);
			}
			model.addRow(count);
		
		return model;
	}

	
	public void setCountTableAlignment() {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		sumT.setRowHeight(35);
		for (int i = 0; i < sumT.getColumnCount(); i++) {
			sumT.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
	}

	public void setBookTableAlignment() {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		result.setRowHeight(30);
		result.getColumnModel().getColumn(0).setPreferredWidth(51);
		result.getColumnModel().getColumn(1).setPreferredWidth(298);
		result.getColumnModel().getColumn(2).setPreferredWidth(147);
		result.getColumnModel().getColumn(3).setPreferredWidth(49);
		result.getColumnModel().getColumn(4).setPreferredWidth(52);
		for (int i = 0; i < result.getColumnCount(); i++) {
			if (i == 1)
				continue;
			result.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
	}

	public DefaultTableModel setBookListTable(String comboText, String keyword) {

		String[] column = { "일련번호", "제목", "지은이", "위치", "대출여부" };
		DefaultTableModel model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int rowIndex, int mColInde) {
				return false;
				}
		};
		booklistDAO books = booklistDAO.getInstance();
		ArrayList<booklistDTO> book;

		if (keyword == null) {
			book = books.selectBook("", "");
		} else {
			book = books.selectBook(comboText, keyword);
		}

		for (booklistDTO b : book) {
			Vector<Object> row = new Vector<Object>();
			row.add(b.getBno());
			row.add(b.getTitle());
			row.add(b.getAuthor());
			row.add(b.getLoc());
			row.add(b.getCon());
			model.addRow(row);
			;
		}
		return model;
	}

	public void setHireListTableAlignment() {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		result.setRowHeight(30);
		result.getColumnModel().getColumn(0).setPreferredWidth(150);
		result.getColumnModel().getColumn(1).setPreferredWidth(100);
		result.getColumnModel().getColumn(2).setPreferredWidth(300);
		result.getColumnModel().getColumn(3).setPreferredWidth(150);
		result.getColumnModel().getColumn(4).setPreferredWidth(80);
		result.getColumnModel().getColumn(5).setPreferredWidth(80);
		result.getColumnModel().getColumn(6).setPreferredWidth(60);
		for (int i = 0; i < result.getColumnCount(); i++) {
			result.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
	}

	public DefaultTableModel setHireListTable(String comboText, String keyword) {

		String[] column = { "이름", "연락처", "제목", "지은이", "대출일자", "반납일자", "일련번호" };
		DefaultTableModel model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int rowIndex, int mColInde) {
				return false;
				}
		};
		hirelistDAO hires = hirelistDAO.getInstance();
		ArrayList<hirelistDTO> hire;

		if (keyword == null) {
			hire = hires.selectBook("", "");
		} else {
			hire = hires.selectBook(comboText, keyword);
		}

		for (hirelistDTO h : hire) {
			Vector<Object> row = new Vector<Object>();
			row.add(h.getMname());
			row.add(h.getPhone());
			row.add(h.getTitle());
			row.add(h.getAuthor());
			row.add(h.getOutdate().substring(0, 10));
			row.add(h.getIndate().substring(0, 10));
			row.add(h.getBno());
			model.addRow(row);
			;
		}
		return model;
	}

	public void setMemberTableAlignment() {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		result.setRowHeight(30);
		result.getColumnModel().getColumn(0).setPreferredWidth(78);
		result.getColumnModel().getColumn(1).setPreferredWidth(140);
		result.getColumnModel().getColumn(2).setPreferredWidth(100);
		result.getColumnModel().getColumn(3).setPreferredWidth(201);
		result.getColumnModel().getColumn(4).setPreferredWidth(78);
		for (int i = 0; i < result.getColumnCount(); i++) {
			result.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}
	}

	public DefaultTableModel setMemberListTable(String comboText, String keyword) {

		String[] column = { "회원번호", "이름", "연락처", "주소", "대출권수" };
		DefaultTableModel model = new DefaultTableModel(column, 0) {
		public boolean isCellEditable(int rowIndex, int mColInde) {
			return false;
			}
		};
			
		memberDAO members = memberDAO.getInstance();
		ArrayList<memberDTO> member;

		if (comboText.equals("이름"))
			member = members.selectMember("이름", keyword);
		else
			member = members.selectMember("연락처", keyword);

		for (memberDTO m : member) {
			Vector<Object> row = new Vector<Object>();
			row.add(m.getMno());
			row.add(m.getMname());
			row.add(m.getPhone());
			row.add(m.getAddr());
			row.add(m.getBcount());
			model.addRow(row);
		}
		return model;
	}

	public void setActionListener(String tableName) {
		if (tableName.equals("도서목록")) {
			// book button has acctionListener
			refresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					result.setModel(setBookListTable("", ""));
					result.changeSelection(result.getRowCount() - 1, 0, false, false);
					sumT.setModel(setCountTable("",""));
					setCountTableAlignment();
					setBookTableAlignment();
				}
			});

			// searchBox 액션 추가
			reqT.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						String keyword = reqT.getText();
						if (reqC.getSelectedItem().toString().equals("제목")) {
							result.setModel(setBookListTable("제목", keyword));
							sumT.setModel(setCountTable("제목", keyword));
							setCountTableAlignment();
							setBookTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
						} else if (reqC.getSelectedItem().toString().equals("지은이")) {
							result.setModel(setBookListTable("지은이", keyword));
							sumT.setModel(setCountTable("지은이", keyword));
							setCountTableAlignment();
							setBookTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
						}
						reqT.setText("");
					}
				}
			});

			// search버튼 액션 추가
			search.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String keyword = reqT.getText();
					if (reqC.getSelectedItem().toString().equals("제목")) {
						result.setModel(setBookListTable("제목", keyword));
						sumT.setModel(setCountTable("제목", keyword));
						setCountTableAlignment();
						setBookTableAlignment();
					} else if (reqC.getSelectedItem().toString().equals("지은이")) {
						result.setModel(setBookListTable("지은이", keyword));
						sumT.setModel(model);
						sumT.setModel(setCountTable("지은이", keyword));
						setCountTableAlignment();
						setBookTableAlignment();
						
					}
					reqT.setText("");
				}
			});

			// 전체목록 조회 버튼 액션 추가
			all.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					result.setModel(setBookListTable("", ""));
					result.changeSelection(0, 0, false, false);
					sumT.setModel(setCountTable("", ""));
					setCountTableAlignment();
					setBookTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
				}
			});
			
			
		} else if (tableName.equals("회원목록")) {
			refresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					result.setModel(setMemberListTable("", ""));
					System.out.println();
					result.changeSelection(result.getRowCount() - 1, 0, false, false);
					setMemberTableAlignment();
				}
			});

			reqT.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						String keyword = reqT.getText();
						if (reqC.getSelectedItem().equals("이름")) {
							result.setModel(setMemberListTable("이름", keyword));
							setMemberTableAlignment();
						} else {
							result.setModel(setMemberListTable("연락처", keyword));
							setMemberTableAlignment();
						}
						reqT.setText("");
					}
				}
			});

			search.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String keyword = reqT.getText();
					if (reqC.getSelectedItem().equals("이름")) {
						result.setModel(setMemberListTable("이름", keyword));
						setMemberTableAlignment();
					} else {
						result.setModel(setMemberListTable("연락처", keyword));
						setMemberTableAlignment();
					}
					reqT.setText("");
				}
			});
			all.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					result.setModel(setMemberListTable("", ""));
					result.changeSelection(0, 0, false, false);
					setMemberTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
				}
			});
		} else if (tableName.equals("대여목록")) {
			// book button has acctionListener
			refresh.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					result.setModel(setHireListTable("", ""));
					result.changeSelection(result.getRowCount() - 1, 0, false, false);
					setHireListTableAlignment();
				}
			});

			// searchBox 액션 추가
			reqT.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						String keyword = reqT.getText();
						if (reqC.getSelectedItem().toString().equals("이름")) {
							result.setModel(setHireListTable("이름", keyword));
	
						} else if (reqC.getSelectedItem().toString().equals("연락처")) {
							result.setModel(setHireListTable("연락처", keyword));
						}
						setHireListTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
						reqT.setText("");
					}
				}
			});

			// search버튼 액션 추가
			search.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String keyword = reqT.getText();
					if (reqC.getSelectedItem().toString().equals("이름")) {
						result.setModel(setHireListTable("이름", keyword));
					} else if (reqC.getSelectedItem().toString().equals("연락처")) {
						result.setModel(setHireListTable("연락처", keyword));						
					}
					setHireListTableAlignment();
					reqT.setText("");
				}
			});

			// 전체목록 조회 버튼 액션 추가
			all.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					result.setModel(setHireListTable("", ""));
					result.changeSelection(0, 0, false, false);
					setHireListTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
				}
			});
		}
	}

}
