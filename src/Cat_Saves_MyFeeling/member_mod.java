package Cat_Saves_MyFeeling;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import DAO.memberDAO;
import DTO.memberDTO;
import Design.JOptionPane_test;

public class member_mod extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel title; // 제목
	private JLabel info; // 회원정보 필수입력 안내
	private JLabel nameL; // 이름
	private JTextField name; // 이름칸
	private JLabel phoneL; // 연락처
	private JTextField phone; // 연락처 칸
	private JLabel addrL; // 주소
	private JTextArea addr;
//	private JTextField addrF; //

	private JButton ok; // 확인
	private JButton cancle; // 취소

	private memberDTO dto;
	private memberDAO dao = memberDAO.getInstance();
	
	
	public static void main(String[] args) {

	//new member_mod();
	}

	protected JTextField setTextField(String name, String placeholder, int x, int y, int width, int height) {

		JTextField txt = new JTextField();

		if (placeholder == null) {
			txt.setText("Please input here");
		} else {
			txt.setText(placeholder);
		}

		Font tfFont = new Font("궁서체", Font.BOLD, 15);
		txt.setFont(tfFont);
		txt.setBackground(Color.white);

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
		txt.setName(name);

		return txt;
	}


	public member_mod() {
		try {
			
//		UIManager.put("OptionPane.messageFont", new Font("궁서체", Font.BOLD, 14));
//		UIManager.put("OptionPane.buttonFont", new Font("궁서체", Font.BOLD, 14));

		UIManager.put("Label.font", new Font("궁서체", Font.BOLD, 12));
		UIManager.put("Button.font", new Font("궁서체", Font.BOLD, 12));
//		UIManager.put("TextArea.Font", new Font("궁서체", Font.BOLD, 12));
//		UIManager.put("TextField.font", new Font("궁서체", Font.BOLD, 12));
		
		
		int row = Management_Member.memberListTable.getSelectedRow();
		Integer mno = (Integer)Management_Member.memberListTable.getValueAt(row, 0);
		dto = dao.selectOne(mno);
		
		getContentPane().setBackground(Color.white);
//		setTitle("회원추가");
//		setUndecorated(true);
		setSize(340, 370);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null); // 프레임 화면중앙에 띄우기
		getContentPane().setLayout(null);

		title = new JLabel("회원 수정");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(0, 10, 324, 68);
		getContentPane().add(title);
		title.setFont(new Font("궁서체", Font.BOLD, 32));

		info = new JLabel("수정할 정보 입력");
		info.setFont(new Font("궁서체", Font.BOLD, 12));
		info.setBounds(176, 70, 105, 26);
		getContentPane().add(info);

		// 이름
		nameL = new JLabel("  이름  : ");
//		nameL.setFont(new Font("궁서체", Font.BOLD, 12));
		nameL.setHorizontalAlignment(JLabel.RIGHT);
		nameL.setBounds(12, 118, 75, 34);
		getContentPane().add(nameL);

		name = new JTextField();
		name = setTextField(null, "한글 or 영어입력", 96, 119, 185, 34);
		name.setText(dto.getMname());
		name.setColumns(12);
		// name.setBounds(96, 126, 185, 34);
		getContentPane().add(name);

		// 연락처
		phoneL = new JLabel("  연락처  : ");
//		phoneL.setFont(new Font("궁서체", Font.BOLD, 12));
		phoneL.setHorizontalAlignment(JLabel.RIGHT);
		phoneL.setBounds(2, 162, 85, 34);
		getContentPane().add(phoneL);

		phone = new JTextField();
		phone = setTextField("phone", "010-xxxx-xxxx 입력", 96, 163, 185, 34);
		phone.setText(dto.getPhone());
		name.setColumns(25);
//		phone.setBounds(96, 170, 185, 34);
		getContentPane().add(phone);

		// 주소
		addrL = new JLabel("  주소  : ");
//		addrL.setFont(new Font("궁서체", Font.BOLD, 12));
		addrL.setHorizontalAlignment(JLabel.RIGHT);
		addrL.setBounds(12, 203, 75, 34);
		getContentPane().add(addrL);

		addr = new JTextArea(0, 5);
		addr.setFont(new Font("궁서체", Font.BOLD, 13));
		addr.setText(dto.getAddr());
		JScrollPane jsp = new JScrollPane(addr, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBounds(96, 207, 185, 45);
		addr.setLineWrap(true);
		getContentPane().add(jsp);

//		addrF = new JTextField();
//		addrF.setEditable(false);
//		addrF.setText(dto.getAddr());
//		addrF.setBounds(108, 270, 138, 21);
//		getContentPane().add(addrF);
//		addrF.setColumns(10);

		// 확인버튼
		ok = new JButton("확인");
//		ok.setFont(new Font("궁서체", Font.BOLD, 12));
		ok.setFocusPainted(false);
		ok.setBounds(55, 275, 85, 26);
		getContentPane().add(ok);

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				boolean a = Pattern.matches("[a-zA-Z가-힣]+", name.getText());
				boolean b = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phone.getText()); // 연락처 -> -- 형식
				boolean c = Pattern.matches("[!@#$%^&*()_+0-9]+", name.getText()); // 이름 -> 한글 or 영어
				

				

				if (name.getText() == null || name.getText().trim().isEmpty()) {
					new JOptionPane_test("이름은 필수 입력 사항입니다.", "이름 확인", JOptionPane.ERROR_MESSAGE);
//					JOptionPane.showMessageDialog(null, "이름에 공백이 있습니다.", "ID 확인", JOptionPane.ERROR_MESSAGE);
					name.requestFocus();
				} else if (a == false) {
					JOptionPane.showMessageDialog(null, "올바른 형식을 입력해주세요.\n숫자, 특수문자 금지!", "이름 확인 ",
							JOptionPane.ERROR_MESSAGE);
					
					name.requestFocus();
				} else if (phone.getText() == null || phone.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "연락처는 필수 입력 사항입니다.", "연락처 확인 ", JOptionPane.ERROR_MESSAGE);
					phone.requestFocus();
				} else if (b == false) {
					JOptionPane.showMessageDialog(null, "010-xxxx-xxxx 입력", "연락처 확인 ", JOptionPane.ERROR_MESSAGE);
					phone.requestFocus();
				} else if (addr.getText() == null || addr.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "주소는 필수 입력 사항입니다.", "주소 확인 ", JOptionPane.ERROR_MESSAGE);
					addr.requestFocus();
				} else if (name.getText() != null && !name.getText().trim().isEmpty() && (a = true)
						&& phone.getText() != null && !phone.getText().contains(" ") && (b = true)
						&& addr.getText() != null && !addr.getText().trim().isEmpty()) {
					dto.setMname(name.getText());
					dto.setPhone(phone.getText());
					dto.setAddr(addr.getText());
//					System.out.println(dto.getMno()+dto.getMname()+dto.getPhone()+dto.getAddr());
					dao.update(dto);

					String[] buttons = { "확인"};
					int result = JOptionPane.showOptionDialog(null, "회원수정이 완료되었습니다!!", "회원수정",
							JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "확인");

					if (result == 0) {
						dispose();
						Management_Member.memberListTable.setModel(Management_Member.setMemberListTable("", ""));
						Management_Member.setMemberTableAlignment();
						Management_Member.memberListTable.changeSelection(
								row, 
								0, 
								false, 
								false);
					}
				}
			}
		});

		// 취소버튼
		cancle = new JButton("취소");
//		cancle.setFont(new Font("궁서체", Font.BOLD, 12));
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
		} catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "정보가 제대로 선택되었는지 확인해주세요.",
					null, JOptionPane.ERROR_MESSAGE);
		}
	}
}
