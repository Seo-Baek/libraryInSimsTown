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

import DAO.booklistDAO;
import DTO.booklistDTO;

public class Management_BookList extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5546268166916129609L;
	
	
	JPanel leftPanel, rightPanel, topPanel, tablePanel, searchPanel;
	JLabel title;
	Font titleFont;
	JButton refresh, searchButton, addBookList, delBookList, viewAllBooks;
	static JTable bookListTable;
	JScrollPane jsp;		
	JComboBox<String> combobox;
	JTextField bookSearchBox;
	
	public Management_BookList() {
		
		init();
		
		// 이벤트 처리 -----------------------------------------------------------------------------------------		
		
		// 새로고침 버튼 액션 추가
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookListTable.setModel(setBookListTable("", ""));
				bookListTable.changeSelection(bookListTable.getRowCount()-1, 0, false, false);
				setBookTableAlignment();				
			}
		});
		
		// searchBox 액션 추가
		bookSearchBox.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					String keyword = bookSearchBox.getText();
					if(combobox.getSelectedItem().toString().equals("제목")) {
						bookListTable.setModel(setBookListTable("제목", keyword));
						setBookTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
					}
					else if(combobox.getSelectedItem().toString().equals("지은이")) {
						bookListTable.setModel(setBookListTable("지은이", keyword));
						setBookTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
					}
					bookSearchBox.setText("");
				}				
			}
		});
		
		// search버튼 액션 추가
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String keyword = bookSearchBox.getText();
				if(combobox.getSelectedItem().toString().equals("제목")) {
					bookListTable.setModel(setBookListTable("제목", keyword));
					setBookTableAlignment();
				}
				else if(combobox.getSelectedItem().toString().equals("지은이")) {
					bookListTable.setModel(setBookListTable("지은이", keyword));
					setBookTableAlignment();
				}
				bookSearchBox.setText("");
			}
		});
		
		// 전체목록 조회 버튼 액션 추가
		viewAllBooks.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				bookListTable.setModel(setBookListTable("", ""));
				bookListTable.changeSelection(0, 0, false, false);
				setBookTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
			}
		});
		
		addBookList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new booklist_add();				
			}
		});
		
		delBookList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = -1;
				row = bookListTable.getSelectedRow();
				if(row > -1) {
					String bno = bookListTable.getValueAt(row, 0).toString(); // 선택한 row 의 0번째 인덱스 값(일련번호)을 불러온다
					String title = bookListTable.getValueAt(row, 1).toString();
					String author = bookListTable.getValueAt(row, 2).toString();
					String[] buttons = { "예", "아니오" };
					int result = JOptionPane.showOptionDialog(null, "삭제할 정보 \n\n제목 : "+ title + 
                            "\n지은이 : "+ author + "\n\n정말 삭제 하시겠습니까?", "삭제 확인",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "예");
					if (result == 0) {
						booklistDAO.getInstance().delBookList(bno);
						bookListTable.setModel(setBookListTable("", ""));
						setBookTableAlignment();
					}				
				} else {
					JOptionPane.showMessageDialog(null, "삭제할 정보가 선택되었는지 확인하세요.", null, JOptionPane.ERROR_MESSAGE);
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
		title = new JLabel("도서목록");
		titleFont = new Font("궁서", Font.BOLD, 30);
		title.setFont(titleFont);
		refresh = new JButton(new ImageIcon("D:/java-study/eclipse-workspace/Project/src/Gallery/refreshButton.png"));
		refresh.setPreferredSize(new Dimension(50, 50));
		
		// 왼편 중앙
		bookListTable = new JTable(setBookListTable("", ""));
		bookListTable.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		bookListTable.getTableHeader().setFont(new Font("궁서", Font.BOLD, 15));
		bookListTable.getTableHeader().setReorderingAllowed(false);
		bookListTable.getTableHeader().setResizingAllowed(false);
		setBookTableAlignment(); // 테이블 컬럼 크기와 내용을 정렬해준다
		jsp = new JScrollPane(bookListTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);		
		
		// 왼편 하단
		 // comboList_Management_Member
		String[] comboList = {"제목", "지은이"};
		combobox = new JComboBox<String>(comboList);
		combobox.setFont(new Font("궁서", Font.BOLD, 20));
		
		bookSearchBox = new JTextField(20);
		bookSearchBox.setFont(new Font("궁서", Font.BOLD, 20));
		searchButton = new JButton("검색");
		searchButton.setFont(new Font("궁서", Font.BOLD, 20));
		
		// 오른편 패널에 들어갈 컴포넌트
		addBookList = new JButton("도서목록 추가");
		addBookList.setFont(new Font("궁서", Font.BOLD, 30));
		delBookList = new JButton("도서목록 삭제");
		delBookList.setFont(new Font("궁서", Font.BOLD, 30));
		viewAllBooks = new JButton("전체목록 조회");
		viewAllBooks.setFont(new Font("궁서", Font.BOLD, 30));
		
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
		searchPanel.add(bookSearchBox, BorderLayout.CENTER);
		searchPanel.add(searchButton, BorderLayout.EAST);
		leftPanel.add(searchPanel, BorderLayout.SOUTH);
		
		// 오른편 패널 세팅
		rightPanel.setLayout(new GridLayout(0, 1, 0, 100));
		rightPanel.setBorder(new EmptyBorder(75, 100, 83, 100));

		rightPanel.add(addBookList);
		rightPanel.add(delBookList);
		rightPanel.add(viewAllBooks);
		
		
		// JSplitPane 생성
		JSplitPane divider = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		divider.setBorder(BorderFactory.createEmptyBorder(80, 130, 80, 40));
		divider.setResizeWeight(0.8);
		divider.setEnabled(false);
		divider.setDividerSize(0);
		
		setLayout(new BorderLayout());
		add(divider);
	}	
	
	static DefaultTableModel setBookListTable(String comboText, String keyword) {
		String[] column = {"일련번호", "제목", "지은이", "위치", "대출여부"};
		DefaultTableModel model = new DefaultTableModel(column, 0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		booklistDAO books = booklistDAO.getInstance();
		ArrayList<booklistDTO> book;
		
		if(keyword == null) {
			book = books.selectBook("", "");
		}else {
			book = books.selectBook(comboText, keyword);
		}
		
		for(booklistDTO b : book) {
			Vector<Object> row = new Vector<Object>();
			row.add(b.getBno());
			row.add(b.getTitle());
			row.add(b.getAuthor());
			row.add(b.getLoc());
			row.add(b.getCon());
			model.addRow(row);;
		}
		return model;		
	}
	
	
	static void setBookTableAlignment() {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		bookListTable.setRowHeight(30);
		bookListTable.getColumnModel().getColumn(0).setPreferredWidth(5);
		bookListTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		bookListTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		bookListTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		bookListTable.getColumnModel().getColumn(4).setPreferredWidth(50);
		for(int i=0; i<bookListTable.getColumnCount(); i++) {
			if(i == 1) continue;
			bookListTable.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}		
	}

	
	public static void main(String[] args) {

		JFrame test = new JFrame();
		JPanel mm = new Management_BookList();
		test.add(mm);
		test.setBounds(100, 100, 1720, 900);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setVisible(true);

	}

}
