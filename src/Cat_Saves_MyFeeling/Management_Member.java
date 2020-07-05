package Cat_Saves_MyFeeling;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DAO.memberDAO;
import DTO.memberDTO;

public class Management_Member extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4164841070068153230L;
	
	
	JPanel leftPanel, rightPanel, topPanel, tablePanel, searchPanel;
	JLabel title;
	Font titleFont;
	JButton refresh, searchButton, addMemberList, editMemberInfo, delMemberList, viewAllMember;
	DefaultTableModel model;
	static JTable memberListTable;
	JScrollPane jsp;		
	JComboBox<String> combobox;
	
	JTextField memberSearchBox;	
	

	
	public Management_Member() {
		init();
		
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memberListTable.setModel(setMemberListTable("", ""));
				System.out.println();
				memberListTable.changeSelection(memberListTable.getRowCount()-1, 0, false, false);
				setMemberTableAlignment();
			}
		});
		
		memberSearchBox.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String keyword = memberSearchBox.getText();
					if(combobox.getSelectedItem().equals("이름")) {
						memberListTable.setModel(setMemberListTable("이름", keyword));
						setMemberTableAlignment();
					}
					else {
						memberListTable.setModel(setMemberListTable("연락처", keyword));
						setMemberTableAlignment();
					}
					memberSearchBox.setText("");
				}
			}
		});
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyword = memberSearchBox.getText();
				if (combobox.getSelectedItem().equals("이름")) {
					memberListTable.setModel(setMemberListTable("이름", keyword));
					setMemberTableAlignment();
				} else {
					memberListTable.setModel(setMemberListTable("연락처", keyword));
					setMemberTableAlignment();
				}
				memberSearchBox.setText("");
			}
		});
		viewAllMember.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				memberListTable.setModel(setMemberListTable("", ""));
				memberListTable.changeSelection(0, 0, false, false);
				setMemberTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
			}
		});
		
		addMemberList.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				new member_add();
			}
		});
		
		editMemberInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new member_mod();
			}
		});
		
		delMemberList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					int row = memberListTable.getSelectedRow();
					String mno = memberListTable.getValueAt(row, 0).toString(); // 선택한 row 의 0번째 인덱스 값(일련번호)을 불러온다
					String mname = (String)memberListTable.getValueAt(row, 1);
					String phone = (String)memberListTable.getValueAt(row, 2);
	
					String[] buttons = { "예", "아니오" };
					int result = JOptionPane.showOptionDialog(null, "삭제할 정보 \n\n이름 : "+ mname + 
							                                         "\n전화번호 : "+ phone + "\n\n정말 삭제 하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, buttons, "예");
					if (result == 0) {
						memberDAO.getInstance().delete(mno);
						memberListTable.setModel(setMemberListTable("", ""));
						setMemberTableAlignment();
	
					} else if(result == 0) {
						JOptionPane.showMessageDialog(null, "다시 선택해주세요.");
					}
				} catch(ArrayIndexOutOfBoundsException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "삭제할 정보가 제대로 선택되었는지 확인해주세요.",
							null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}
	
	public void init() {
		
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		topPanel = new JPanel();
		tablePanel = new JPanel();
		searchPanel = new JPanel();
		
		// 왼편 상단 패널에 들어갈 컴포넌트
		title = new JLabel("회원목록");
		titleFont = new Font("궁서", Font.BOLD, 30);
		title.setFont(titleFont);
		refresh = new JButton(new ImageIcon("D:/java-study/eclipse-workspace/Project/src/Gallery/refreshButton.png"));
		refresh.setPreferredSize(new Dimension(50, 50));
		
		// 왼편 중앙
		memberListTable = new JTable(setMemberListTable("", ""));
		memberListTable.getTableHeader().setFont(new Font("궁서", Font.BOLD, 15));
		memberListTable.getTableHeader().setReorderingAllowed(false);
		memberListTable.getTableHeader().setResizingAllowed(false);
		setMemberTableAlignment();
		memberListTable.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		
		jsp = new JScrollPane(memberListTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		
		// 왼편 하단
		String[] comboList = {"이름", "연락처"}; // comboList_Management_Member
		combobox = new JComboBox<String>(comboList);
		combobox.setFont(new Font("궁서", Font.BOLD, 20));
		
		memberSearchBox = new JTextField(20);
		memberSearchBox.setFont(new Font("궁서", Font.BOLD, 20));
		searchButton = new JButton("검색");
		searchButton.setFont(new Font("궁서", Font.BOLD, 20));
		
		// 오른편 패널에 들어갈 컴포넌트
		addMemberList = new JButton("신규회원 추가");
		addMemberList.setFont(new Font("궁서", Font.BOLD, 30));
		editMemberInfo = new JButton("회원정보 변경");
		editMemberInfo.setFont(new Font("궁서", Font.BOLD, 30));
		delMemberList = new JButton("회원정보 삭제");
		delMemberList.setFont(new Font("궁서", Font.BOLD, 30));
		viewAllMember = new JButton("전체회원 조회");
		viewAllMember.setFont(new Font("궁서", Font.BOLD, 30));
		
		// 왼편 패널 세팅
		leftPanel.setLayout(new BorderLayout(0, 20));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// 왼편 패널 상단에 toppanel 추가
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(title);
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(refresh);
		leftPanel.add(topPanel, BorderLayout.NORTH);
		
		// 왼편 패널 중앙에 테이블 추가
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(jsp);
		tablePanel.setBorder(new EmptyBorder(0, 0, 20, 0));
		leftPanel.add(tablePanel, BorderLayout.CENTER);
		
		// 왼편패널 하단에 searchpanel 추가
		searchPanel.setLayout(new BorderLayout(20, 0));
		searchPanel.setBorder(new EmptyBorder(0, 200, 0, 200));
		searchPanel.add(combobox, BorderLayout.WEST);
		searchPanel.add(memberSearchBox, BorderLayout.CENTER);
		searchPanel.add(searchButton, BorderLayout.EAST);
		leftPanel.add(searchPanel, BorderLayout.SOUTH);
		
		// 오른편 패널 세팅
		rightPanel.setLayout(new GridLayout(0, 1, 0, 35));
		rightPanel.setBorder(new EmptyBorder(75, 100, 83, 100));

		rightPanel.add(addMemberList);
		rightPanel.add(editMemberInfo);
		rightPanel.add(delMemberList);
		rightPanel.add(viewAllMember);
		
		
		// JSplitPane 생성
		JSplitPane divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		divider.setBorder(BorderFactory.createEmptyBorder(80, 130, 80, 40));
		divider.setResizeWeight(0.8);
		divider.setEnabled(false);
		divider.setDividerSize(0);
			
		setLayout(new BorderLayout());
		add(divider);		
	}
	
	static DefaultTableModel setMemberListTable(String comboText, String keyword) {
		String[] column = {"회원번호", "이름", "연락처", "주소", "대출권수"};
		DefaultTableModel model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		memberDAO members = memberDAO.getInstance();		
		ArrayList<memberDTO> member;

		if(comboText.equals("이름"))
			member = members.selectMember("이름", keyword);
		else
			member = members.selectMember("연락처", keyword);
		
		for(memberDTO m : member) {
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
	
	static void setMemberTableAlignment() {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		memberListTable.setRowHeight(30);
		memberListTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		memberListTable.getColumnModel().getColumn(3).setPreferredWidth(500);
		memberListTable.getColumnModel().getColumn(4).setPreferredWidth(20);
		for(int i=0; i<memberListTable.getColumnCount(); i++) {
			memberListTable.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame test = new JFrame();
		JPanel mm = new Management_Member();
		test.add(mm);
		test.setBounds(100, 100, 1720, 900);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setVisible(true);
		

	}

}
