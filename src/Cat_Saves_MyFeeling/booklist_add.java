 package Cat_Saves_MyFeeling;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import DAO.booklistDAO;
import DTO.booklistDTO;

public class booklist_add extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel title; //
	private JLabel info; // 회원정보 필수입력 안내
	private JLabel bookTitleL; // 책제목
	private JTextField bookTitle; // 책제목칸
	private JLabel authorL; // 저자
	private JTextField author; // 저자 칸
	private JLabel locL; // 장소
	private JTextField loc; // 장소 칸

	private JButton ok; // 확인
	private JButton cancle; // 취소

	private booklistDTO dto = new booklistDTO();
	private booklistDAO dao = booklistDAO.getInstance();

	public static void main(String[] args) {

		new booklist_add();
	}

	protected JTextField setTextField(String bookTitle, String placeholder, int x, int y, int width, int height) {

		JTextField txt = new JTextField();
		
		if (placeholder == null) {
			txt.setText("Please input here");
		} else {
			txt.setText(placeholder);
		}

		Font tfFont = new Font("궁서체", Font.BOLD, 15);
		txt.setFont(tfFont);
		txt.setBackground(Color.white);
		txt.setForeground(Color.gray.brighter());

		txt.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				JTextField tf = (JTextField) e.getComponent();
				if (tf.getText().equals("")) {
					if (placeholder == null) {
						tf.setForeground(Color.gray.brighter());
//						tf.setText("Please input here");
					} else {
						tf.setForeground(Color.gray.brighter());
						tf.setText(placeholder);
					}
				}
			}

			public void focusGained(FocusEvent e) {
				JTextField tf = (JTextField) e.getComponent();
				if (tf.getText().equals(placeholder) || tf.getText().equals("Please input here")
						|| tf.getText().equals("")) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}
		});

		txt.setBounds(x, y, width, height);
//	      jp.add(txt);
		txt.setName(bookTitle);

		return txt;
	}


	public booklist_add() {
		UIManager.put("OptionPane.messageFont", new Font("궁서체", Font.BOLD, 14));
		UIManager.put("OptionPane.buttonFont", new Font("궁서체", Font.BOLD, 14));
		
		UIManager.put("Label.font", new Font("궁서체", Font.BOLD, 12));
		UIManager.put("Button.font", new Font("궁서체", Font.BOLD, 12));
		
		getContentPane().setBackground(Color.white);
		
//		setTitle("신간추가");
//		setUndecorated(true);
		setSize(340, 370);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 프레임 화면중앙에 띄우기
		getContentPane().setLayout(null);
		
		title = new JLabel("신간 추가");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 10, 324, 68);
		getContentPane().add(title);
		title.setFont(new Font("궁서체", Font.BOLD, 32));
		
		info = new JLabel("책 정보 입력 (* 표시는 필수입력)");
		info.setFont(new Font("궁서체", Font.BOLD, 12));
		info.setBounds(69, 77, 216, 26);
		getContentPane().add(info);
		
		// 책 제목
		bookTitleL = new JLabel("* 책제목  : ");
		bookTitleL.setHorizontalAlignment(JLabel.RIGHT);
		bookTitleL.setBounds(0, 126, 85, 34);
		getContentPane().add(bookTitleL);
		
		bookTitle = new JTextField();
		bookTitle = setTextField(null, "", 96, 126, 185, 34);
		bookTitle.setColumns(10);
		// bookTitle.setBounds(96, 126, 185, 34);
		getContentPane().add(bookTitle);
		
		// 저자
		authorL = new JLabel("* 지은이  : ");
		authorL.setHorizontalAlignment(JLabel.RIGHT);
		authorL.setBounds(2, 169, 85, 34);
		getContentPane().add(authorL);
		
		author = new JTextField();
		author = setTextField("author", " ", 96, 170, 185, 34);
		author.setColumns(25);
//		author.setBounds(96, 170, 185, 34);
		getContentPane().add(author);
		
		// 장소
		locL = new JLabel("* 장소  : ");
		locL.setHorizontalAlignment(JLabel.RIGHT);
		locL.setBounds(12, 210, 75, 34);
		getContentPane().add(locL);
		
		loc = new JTextField();
		loc = setTextField("loc", "ex) A", 96, 214, 185, 34);
		loc.setColumns(25);
		getContentPane().add(loc);
		
//		locF = new JTextField();
//		locF.setEditable(false);
//		locF.setText(dto.getloc());
//		locF.setBounds(108, 270, 138, 21);
//		getContentPane().add(locF);
//		locF.setColumns(10);
		
		// 확인버튼
		ok = new JButton("확인");
		ok.setFocusPainted(false);
		ok.setBounds(69, 275, 85, 26);
		getContentPane().add(ok);
		
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean a = Pattern.matches("[A-Z가-힣]+", loc.getText()); // 이름 -> 한글 or 영어
				
				
				if (bookTitle.getText() == null || bookTitle.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "제목은 필수 입력 사항입니다.", "제목 확인", JOptionPane.ERROR_MESSAGE);
					bookTitle.requestFocus();
				} else if (author.getText() == null || author.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "지은이는 필수 입력 사항입니다.", "지은이 확인", JOptionPane.ERROR_MESSAGE);
					author.requestFocus();
				} else if (loc.getText() == null || loc.getText().contains(" ")) {
					JOptionPane.showMessageDialog(null, "장소는 필수 입력 사항입니다.", "장소 확인", JOptionPane.ERROR_MESSAGE);
					loc.setText("");
					loc.requestFocus();
				} else if (a == false) {
					JOptionPane.showMessageDialog(null, "대문자로 입력해주세요.\n숫자, 특수문자 금지!", "장소 확인 ",
							JOptionPane.ERROR_MESSAGE);
					loc.setText("");
					loc.requestFocus();
				} else if (bookTitle.getText() != null && !bookTitle.getText().trim().isEmpty() &&
						author.getText() != null && !author.getText().trim().isEmpty() && 
						loc.getText() != null && !loc.getText().trim().isEmpty() && (a=true)) {
					dto.setTitle(bookTitle.getText());
					dto.setAuthor(author.getText());
					dto.setLoc(loc.getText());
					try {
						dao.Insert(dto);
						
						String[] buttons = {"예", "아니오"};
						int result = JOptionPane.showOptionDialog(null, "신간추가가 완료되었습니다!!\n더 추가 하시겠습니까?", "신간추가",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "확인");
						if (result == 1) {
							dispose();
							// 신간추가 창을 닫은 이후에 booklisttable의 값을 다시 설정 해 준다
							Management_BookList.bookListTable.setModel(Management_BookList.setBookListTable("", ""));
							// 테이블 가장 마지막 행을 선택한다
							Management_BookList.bookListTable.changeSelection(
									Management_BookList.bookListTable.getRowCount()-1, 
									0, 
									false, 
									false);
							Management_BookList.setBookTableAlignment(); // 테이블의 행열 간격을 정렬한다
							
						} else if (result == 0) {
							bookTitle.setText("");
							author.setText("");
							loc.setText("");
							
							bookTitle.requestFocus();
							
							Management_BookList.bookListTable.setModel(Management_BookList.setBookListTable("", ""));
							
							Management_BookList.bookListTable.changeSelection(
									Management_BookList.bookListTable.getRowCount()-1, 
									0, 
									false, 
									false);
							Management_BookList.setBookTableAlignment();
							
						}
					}  catch (SQLException e1) {
						System.out.println(e1.getMessage());
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "데이터를 삽입하는데 실패했습니다. \n관리자에게 문의하세요.", null, JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
			}
		});
		
		// 취소버튼
		cancle = new JButton("취소");
//				      cancle.setBorderPainted(false);
//				      cancle.setContentAreaFilled(false);
		cancle.setFocusPainted(false);
		cancle.setBounds(184, 275, 85, 26);
		getContentPane().add(cancle);
		
		cancle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "창을 닫으시겠습니까? \n확인을 누르시면 창을 닫습니다.", "취소",
						JOptionPane.WARNING_MESSAGE);
				dispose();
				
			}
			
		});
		
		setVisible(true);
	}
}
