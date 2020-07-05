package Cat_Saves_MyFeeling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import DAO.hirelistDAO;
import Design.JPanelWithBackground;

@SuppressWarnings("serial")
public class StartView extends JFrame {

	JPanel insteadmenu;
	JPanel bar;
	JPanel menuset;
	JPanel back;
	JPanel pretty;
	JPanel bManage;
	JPanel mManage;
	

	JSplitPane jspane;
	JPanel left;
	ComponentTest leftL;
	Clock leftR;
	ComponentTest rightL;
	ComponentTest rightR;
	JPanel right;

	JButton forFeeling;
	JButton member;
	JButton book;
	JButton hire;
	JButton refresh;

	// TEST-BSJ -----setname
	JButton lock;
	String pwd = "" + 1000;
	String tempt;

	int paneIndex;
	Random random = new Random();

	String[] hireReq = { "이름", "연락처" };
	String[] bookReq = { "제목", "지은이" };
	String[] hiretitle = { "이름", "연락처", "제목", "저자", "대출일자", "반납일자" };
	String[] booktitle = { "일련번호", "제목", "지은이", "위치", "대여여부" };
	String[] membertitle = { "회원번호", "이름", "연락처", "주소", "대여권수" };

	DefaultTableModel hireTM = new DefaultTableModel(hiretitle, 0);
	DefaultTableModel bookTM = new DefaultTableModel(booktitle, 0);
	DefaultTableModel memberTM = new DefaultTableModel(membertitle, 0);

	String hee = "D:/java-study/eclipse-workspace/Project/src/Gallery/언니네고양이.jpg";
	String ya = "D:/java-study/eclipse-workspace/Project/src/Gallery/희야데쭈.jpg";
	String minwoo = "D:/java-study/eclipse-workspace/Project/src/Gallery/민우오빠랰ㅋ.jpg";
	String seol = "D:/java-study/eclipse-workspace/Project/src/Gallery/설아.png";
	String ah = "D:/java-study/eclipse-workspace/Project/src/Gallery/위풍당당설아.jpg";
	String heeya_seolah = "D:/java-study/eclipse-workspace/Project/src/Gallery/희야랑설아ㅜㅜ.jpg";
	String pu = "D:/java-study/eclipse-workspace/Project/src/Gallery/푸름이1.jpg";
	String rum = "D:/java-study/eclipse-workspace/Project/src/Gallery/푸름이혼난닼ㅋㅋ.jpg";
	String ee = "D:/java-study/eclipse-workspace/Project/src/Gallery/활발한푸름.png";

	String heeya = "D:/java-study/eclipse-workspace/Project/src/Gallery/패널용희야.jpg";
	String seolah = "D:/java-study/eclipse-workspace/Project/src/Gallery/패널용설아.jpg";
	String purumee = "D:/java-study/eclipse-workspace/Project/src/Gallery/패널용푸름이.jpg";
	String heeyawhithselah = "D:/java-study/eclipse-workspace/Project/src/Gallery/패널용희야설아.jpg";

	public static void main(String[] args) {

		new StartView();

	}

	public StartView() {
		UIManager.put("OptionPane.messageFont", new Font("궁서체", Font.BOLD, 14));
		UIManager.put("OptionPane.buttonFont", new Font("궁서체", Font.BOLD, 14));
		
		// Start Setting Frame
		setTitle("Library In SimsonTown");
		ImageIcon top = new ImageIcon("D:/java-study/eclipse-workspace/Project/src/Gallery/고냥이발바닥.png");
		setIconImage(top.getImage());
		
		  try {
		     back = new JPanelWithBackground("D:/java-study/eclipse-workspace/Project/src/Gallery/표지.png");
		     back.setName("back");
		  } catch (IOException e1) {
		     e1.printStackTrace();
		  }
		  back.setLayout(null);
		  lock = new JButton("로그인");
		  lock.setBounds(1510, 50, 100, 40);
		  back.add(lock);
		  

		 try {
			 bar = new JPanelWithBackground("D:/java-study/eclipse-workspace/Project/src/Gallery/menubar.png");
		 } catch (IOException e1) {
		    e1.printStackTrace();
		 }
		 insteadmenu = new JPanel(new BorderLayout());
		 menuset = new JPanel(new GridLayout(0, 4));
		 setLayout(null);
	  
		 //ImageIcon menu = new ImageIcon("D:/java-study/eclipse-workspace/Project/src/Cat_Saves_MyFeeling/menubar.png");
		  
		 forFeeling = new JButton("기분전환 :)");
		 book = new JButton("도서관리");
		 hire = new JButton("대출/반납");
		 member = new JButton("회원관리");
		 
		 
		 menuset.add(forFeeling);
		 menuset.add(book);
		 menuset.add(hire);
		 menuset.add(member);
		 
		
		 insteadmenu.add(menuset,BorderLayout.WEST);
		 insteadmenu.add(bar, BorderLayout.CENTER); 
		  
		  add(insteadmenu);
		  add(back);
		  insteadmenu.setBounds(0, 0, 1720, 35);
		  back.setBounds(0, 35, 1720, 900);
		  
		  
		  setBounds(100,100,1720,900);

		  setResizable(false);

		  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		  setVisible(true);
		  
		  
		  refresh = new JButton("▷◀");
		  refresh.setToolTipText("랜덤 이미지 호출!");
		  refresh.setBounds(1580, 20, 60, 40);
		  
		  // Blocking Displays
		  if(lock.getText().equals("로그인")) {
			  forFeeling.setEnabled(false); book.setEnabled(false);
			  hire.setEnabled(false); member.setEnabled(false);
		  }
		 
		  
		  
		  pretty = new JPanel();
		  
		  paneIndex = 0;
		  
		  // 메뉴 이벤트 처리
		  // 기분전환:) 화면 구현
		  forFeeling.addActionListener(new ActionListener() {

				

				@Override

				public void actionPerformed(ActionEvent e) {
					
					removeCom(paneIndex);
					paneIndex = 9;
					setRandomicPanel();
					pretty.add(lock);
					lock.setBounds(1560, 700, 100, 40);
					revalidate();
				}

			});
		  
		  // 새로고침 화면 구현
		  refresh.addActionListener(new ActionListener() {

				@Override

				public void actionPerformed(ActionEvent e) {
				removeCom(paneIndex);
				setRandomicPanel();
				pretty.add(lock);
				lock.setBounds(1560, 700, 100, 40);
				revalidate(); 
				}

			});
		  
		  // 도서관리 화면 구현
		  book.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeCom(paneIndex);
				bManage = new Management_BookList();
				add(bManage);
				bManage.setBounds(0, 35, 1720, 865);
				revalidate();
				paneIndex = 11;
				
			}
		});
		  
		  // 대출/반납 화면 구현
		  hire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//TEST-BSJ-Start-----------------
				removeCom(paneIndex);
				
				
				// TEST-BSJ Start ---------------
				left = new JPanel();
				left.setLayout(null);
				left.setSize(1720, 865);
				
				leftR = new Clock();
				leftL = new ComponentTest();
				rightL = new ComponentTest();
				rightR = new ComponentTest();
				
				right = new JPanel();
				right.setLayout(null);
				right.setSize(1720,865);
				
				left.add(leftL);
				left.add(leftR);
				leftL.setBounds(0, 0, 855, 865);
				leftR.setBounds(855, 0, 855, 865);
				
				jspane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, left, right);
				jspane.setResizeWeight(0.5);
				jspane.setDividerSize(10);
				jspane.setOneTouchExpandable(true);
			
				
				
				//Define Components
				leftL.setTitle("대여 목록", hireReq, "반납", 550);
				leftL.set3ButtonLoc();
				rightR.setTitle("회원 목록", hireReq, "", 550);
				rightR.set2Buttonloc();
				rightL.setTitle("도서 목록", bookReq, "대출", 500);
				rightL.set3ButtonLoc();
				rightL.addTable(rightL);
				
				// Set TableModel 
				leftL.result.setModel(leftL.setHireListTable("", ""));
				leftL.setHireListTableAlignment();	
				
				rightL.result.setModel(rightL.setBookListTable("", ""));
				rightL.setBookTableAlignment();		
				rightL.sumT.setModel(rightL.setCountTable("",""));
				rightL.setCountTableAlignment();
				// Add TableForCount 
				rightL.addTable(rightL);
				
				rightR.result.setModel(rightR.setMemberListTable("", ""));
				rightR.setMemberTableAlignment();		
				
				// SetBounds
				rightL.setBounds(0, 0, 855, 865);
				rightR.setBounds(855, 0, 855, 865);
				
				//Add Action
				rightL.rent.setToolTipText("구분선을 왼편으로 옮겨 책 목록과 고객을 찾은 후 진행.");
				rightL.setActionListener("도서목록");
				rightR.setActionListener("회원목록");
				leftL.setActionListener("대여목록");
				
				rightL.sumT.getTableHeader().setReorderingAllowed(false); // 이동불가
				rightL.sumT.getTableHeader().setResizingAllowed(false); //크기 조절 불가
				
				
				right.add(rightL, BorderLayout.CENTER);
				right.add(rightR, BorderLayout.WEST);
				
				jspane.setBounds(0, 33, 1720, 865);
				
				
				add(jspane);
				revalidate(); 
				
				paneIndex = 10;
				
				// Set rent Button's Action
				rightL.rent.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						Integer bno = 0; Integer mno = 0;
						String title = null; String name = null;
						
						try {
							bno = (Integer) rightL.result.getValueAt(rightL.result.getSelectedRow(),0);
							mno = (Integer) rightR.result.getValueAt(rightR.result.getSelectedRow(), 0);
							title = (String) rightL.result.getValueAt(rightL.result.getSelectedRow(),1);
							name = (String) rightR.result.getValueAt(rightR.result.getSelectedRow(), 1);
						} catch(ArrayIndexOutOfBoundsException e1) {
							e1.getStackTrace();
							
						}
						if(title != null && name != null) {
							String[] buttons = {"예", "아니오"};
							String ask = "선택한 정보가 맞는지 확인하세요. \n\n이름 : " + name + "\n제목 : " + title + "\n\n선택한 사항이 맞습니까?";
						
							int res = JOptionPane.showOptionDialog(null, ask, "대출 옵션 확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "예");
							if(res == 0) {
								try {
									hirelistDAO hireDAO = hirelistDAO.getInstance();
									hireDAO.Insert(bno, mno);
									hireDAO.UpdateAll("대출", "out", bno);
									//rightL rightR에 대한 새로고침(reselect)문을 불러주면 
									setAllTable(bno, mno);
									leftL.result.setModel(leftL.setHireListTable("", ""));
									leftL.result.changeSelection(leftL.result.getRowCount() - 1, 0, false, false);
									leftL.setHireListTableAlignment();
								}  catch (SQLException e1) {
									// TODO Auto-generated catch block
									System.out.println(e1.getMessage());
									e1.printStackTrace();
									JOptionPane.showMessageDialog(null, "대여에 실패하였습니다. \n선택된 도서가 대출상태인지 확인해 주세요.\n"
											                                      +     "대출상태가 아니라면 관리자에게 확인해주세요.",
											                          null , JOptionPane.ERROR_MESSAGE);
								}
										
									
								} else if(res == 1) {
									JOptionPane.showMessageDialog(null, "다시 선택해주세요.");
								}
						} else {
							JOptionPane.showMessageDialog(null, "대여를 위한 정보가 제대로 선택되었는지 확인해주세요.",
															null, JOptionPane.ERROR_MESSAGE);
						}
					}
				});

				leftL.rent.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						Integer bno = 0;
						String name = null; String title = null;
						
						try {
							
							bno = (Integer) leftL.result.getValueAt(leftL.result.getSelectedRow(), 6);
							title = (String) leftL.result.getValueAt(leftL.result.getSelectedRow(),0);
							name = (String) leftL.result.getValueAt(leftL.result.getSelectedRow(), 2);
							
						} catch (ArrayIndexOutOfBoundsException e1) {
							e1.printStackTrace();
							
						}
						if(title != null && name != null) {
							String[] buttons = {"예", "아니오"};
							String ask = "선택한 정보가 맞는지 확인하세요. \n\n이름 : " + name + "\n제목 : " + title + "\n\n선택한 사항이 맞습니까?";
							int res = JOptionPane.showOptionDialog(null, ask, "반납 옵션 확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, "예");
							if(res == 0) {
							
							hirelistDAO hireDAO = hirelistDAO.getInstance();
							hireDAO.delHireList(bno);
							hireDAO.UpdateAll("반납", "in", bno);
							
							//Set point for member
							Integer mno = hireDAO.selectMember((String)leftL.result.getValueAt(leftL.result.getSelectedRow(),0),
									(String)leftL.result.getValueAt(leftL.result.getSelectedRow(),1));
							
							// refresh area
							leftL.result.setModel(leftL.setHireListTable("", ""));
							leftL.setHireListTableAlignment();
							
							setAllTable(bno, mno);
							JOptionPane.showMessageDialog(null, "반납되었습니다.");
						} else if(res == 1) {
							JOptionPane.showMessageDialog(null, "다시 선택해주세요.");
						}
							
						} else {
							JOptionPane.showMessageDialog(null, "반납할 정보가 선택되었는지 확인하세요.", null, JOptionPane.ERROR_MESSAGE);
						}
						
					}
				});
		  
				
			}
		});
		  
		member.addActionListener(new ActionListener() {

	@Override
			public void actionPerformed(ActionEvent e) {
				
				removeCom(paneIndex);
				mManage = new Management_Member();
				add(mManage);
				mManage.setBounds(0, 35, 1720, 865);
				revalidate();
				paneIndex = 12;
			}
		});
		  
		lock.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(lock.getText().equals("화면 잠금")) {
//					
					pwd = JOptionPane.showInputDialog("네 자리 숫자인 비밀번호를 입력해주세요.");
					int compare = 0;
					if(!pwd.equals(""))
						compare = Integer.parseInt(pwd);
					if(pwd != null) {
						if(compare > 999 && compare < 10000) {
							if(paneIndex == 0) {
								lock.setText("로그인");
							} else {
								lock.setText("화면 풀기");
							}
							
							forFeeling.setEnabled(false); book.setEnabled(false);
							hire.setEnabled(false); member.setEnabled(false);
							
						} else {
							JOptionPane.showMessageDialog(null, "네 자리 숫자인 비밀번호를 입력해주세요.");
							forFeeling.setEnabled(true); book.setEnabled(true);
							hire.setEnabled(true); member.setEnabled(true);
						}
					
					} else if(pwd.equals("")) {
						JOptionPane.showMessageDialog(null, "네 자리 숫자인 비밀번호를 입력해주세요.");
						forFeeling.setEnabled(true); book.setEnabled(true);
						hire.setEnabled(true); member.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호가 입력되지 않았습니다.");
						forFeeling.setEnabled(true); book.setEnabled(true);
						hire.setEnabled(true); member.setEnabled(true);
					}
					
				}  else if(lock.getText().equals("화면 풀기") || lock.getText().equals("로그인")) {
					tempt = JOptionPane.showInputDialog("비밀번호를 입력하세요.");
					if(tempt.equals(pwd)) {
//						if(paneIndex == 0) {
//							lock.setText("로그 아웃");
//						} else {
							lock.setText("화면 잠금");
//						}
						
						forFeeling.setEnabled(true); book.setEnabled(true);
						hire.setEnabled(true); member.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호가 잘못 입력되었습니다.");
					}
					
				}// first if end
			}//actionPerformed end
		});  
		
		
		
		  
	}// 생성자 end

	// 필요 mothod 정리
	public void setPanel(JPanel jp) {

		jp.add(back);
		back.setBounds(550, 75, 710, 670);
		add(jp);
		jp.setBounds(0, 35, 1720, 900);
		jp.setLayout(null);

	}

	// 기분전환:) 패널 랜덤으로 보여주는 메소드
	public void setRandomicPanel() {

		if (paneIndex == 1) {

			try {
				pretty = new JPanelWithBackground(heeya);
				back = new JPanelWithBackground(hee);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			pretty.add(refresh);

			setPanel(pretty);
			setBackground(Color.PINK);
			paneIndex = random.nextInt(9) + 1;

		} else if (paneIndex == 2) {

			pretty = new JPanel();
			try {
				back = new JPanelWithBackground(minwoo);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
			pretty.add(refresh);

			setPanel(pretty);
			pretty.setBackground(Color.DARK_GRAY);
			setBackground(Color.BLACK);
			paneIndex = random.nextInt(9) + 1;

		} else if (paneIndex == 3) {

			try {
				pretty = new JPanelWithBackground(heeya);
				back = new JPanelWithBackground(ya);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			pretty.add(refresh);

			setPanel(pretty);
			setBackground(Color.PINK);
			paneIndex = random.nextInt(9) + 1;

		} else if (paneIndex == 4) {

			try {
				pretty = new JPanelWithBackground(seolah);
				back = new JPanelWithBackground(seol);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			pretty.add(refresh);

			setPanel(pretty);
			setBackground(Color.PINK);
			paneIndex = random.nextInt(9) + 1;

		} else if (paneIndex == 5) {

			try {
				pretty = new JPanelWithBackground(seolah);
				back = new JPanelWithBackground(ah);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			pretty.add(refresh);

			setPanel(pretty);
			setBackground(Color.PINK);
			paneIndex = random.nextInt(9) + 1;

		} else if (paneIndex == 6) {

			try {
				pretty = new JPanelWithBackground(purumee);
				back = new JPanelWithBackground(pu);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			pretty.add(refresh);

			setPanel(pretty);
			setBackground(Color.PINK);
			paneIndex = random.nextInt(9) + 1;

		} else if (paneIndex == 7) {

			try {
				pretty = new JPanelWithBackground(purumee);
				back = new JPanelWithBackground(rum);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			pretty.add(refresh);

			setPanel(pretty);
			setBackground(Color.PINK);
			paneIndex = random.nextInt(9) + 1;

		} else if (paneIndex == 8) {

			try {
				pretty = new JPanelWithBackground(purumee);
				back = new JPanelWithBackground(ee);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			pretty.add(refresh);

			setPanel(pretty);
			setBackground(Color.PINK);
			paneIndex = random.nextInt(9) + 1;

		} else if (paneIndex == 9) {

			try {
				pretty = new JPanelWithBackground(heeyawhithselah);
				back = new JPanelWithBackground(heeya_seolah);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			pretty.add(refresh);

			setPanel(pretty);
			setBackground(Color.PINK);
			paneIndex = random.nextInt(9) + 1;
		}
	}

	// 패널을 지우는 메소드
	public void removeCom(int paneIndex) {
		System.out.println(paneIndex);
		if (paneIndex == 0) {
			remove(back);
		} else if (paneIndex >= 1 && paneIndex <= 9) {
			remove(pretty);

		} else if (paneIndex == 10) {
			remove(jspane);

		} else if (paneIndex == 11) {
			remove(bManage);
		} else if (paneIndex == 12) {
			remove(mManage);
		}

	}
	
	public void setAllTable(Integer bno, Integer mno) {
		
		
		rightL.result.setModel(rightL.setBookListTable("", ""));
		rightR.result.setModel(rightR.setMemberListTable("", ""));
		leftR.result.setModel(leftR.setHireListTable());
		leftR.setHireListTableAlignment();	
		rightL.setBookTableAlignment();
		rightR.setMemberTableAlignment();
		rightL.sumT.setModel(rightL.setCountTable("",""));
		rightL.setCountTableAlignment();
		int selectedSome = 0;
		Integer resultSome = 0;
		for(int i = 0; i < rightL.result.getRowCount(); i++) {
			resultSome = (Integer)rightL.result.getValueAt(i,0);
			if(resultSome == bno) {
				selectedSome = i;
				break;
			}
		}
		rightL.result.changeSelection(selectedSome, 0, false, false);
		
		for(int i = 0; i < rightR.result.getRowCount(); i++) {
			resultSome = (Integer)rightR.result.getValueAt(i,0);
			if(resultSome == mno) {
				selectedSome = i;  
				break;
			}
		}
		rightR.result.changeSelection(selectedSome, 0, false, false);
	}
}
