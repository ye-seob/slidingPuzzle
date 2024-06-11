package views; // View 패키지 선언

import javax.swing.*; // Swing 패키지 import
import java.awt.*; // AWT 패키지 import
import java.awt.event.ActionListener; // ActionListener import
import java.net.URL; // URL import

public class MainView extends JFrame { // MainView 클래스 선언
	private final JButton easyButton = new JButton("3x3"); // 3x3 버튼 선언
	private final JButton mediumButton = new JButton("4x4"); // 4x4 버튼 선언
	private final JButton hardButton = new JButton("5x5"); // 5x5 버튼 선언
	private final JButton customButton = new JButton("Custom"); // 커스텀 버튼 선언
	private final JButton timeAttackButton = new JButton("Time Attack"); // 타임어택 버튼 선언
	private final JButton moveLimitButton = new JButton("Move Limit"); // 움직임 제한 모드 버튼 선언
	private final JButton impossibleButton = new JButton("Impossible Mode"); // 임파서블 모드 버튼 선언
	private final JButton rankingButton = new JButton("Rankings"); // 랭킹 버튼 선언

	private final JPanel mainPanel = new JPanel(); // 메인 패널 선언
	private Image backgroundImg; // 배경 이미지 선언

	public MainView() { // 생성자 정의
		setTitle("슬라이딩 퍼즐"); // 타이틀 설정
		setSize(900, 800); // 사이즈 설정

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 옵션 설정
		mainPanel.setLayout(new BorderLayout()); // 레이아웃 설정

		JPanel backgroundPanel = createBackground(); // 배경 패널 생성
		mainPanel.add(backgroundPanel, BorderLayout.CENTER); // 배경 패널을 중앙에 추가
		getContentPane().add(mainPanel); // 메인 패널을 컨텐트 팬에 추가
	}

	public JPanel getMainPanel() { // 메인 패널 반환 메서드
		return mainPanel; // 메인 패널 반환
	}

	private JPanel createBackground() { // 배경 패널 생성 메서드
		JPanel backgroundPanel = new JPanel() { // 익명 내부 클래스 정의
			{ // 초기화 블록 시작
				URL imageUrl = getClass().getClassLoader().getResource("images/하늘 배경.png"); // 이미지 URL 가져오기
				backgroundImg = new ImageIcon(imageUrl).getImage(); // 배경 이미지 설정
				setLayout(new GridBagLayout()); // 레이아웃 설정
				GridBagConstraints gbc = createGridBagConstraints(); // 그리드백 설정 생성
				addBackground(this, gbc); // 배경 패널에 컴포넌트 추가
			}

			@Override
			protected void paintComponent(Graphics g) { // 그래픽 요소 그리기 메서드 재정의
				super.paintComponent(g); // 상위 클래스 호출
				g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 그리기
			}
		};
		return backgroundPanel; // 배경 패널 반환
	}

	private GridBagConstraints createGridBagConstraints() { // 그리드백 설정 생성 메서드
		GridBagConstraints gbc = new GridBagConstraints(); // 그리드백 설정 객체 생성
		gbc.insets = new Insets(10, 10, 10, 10); // 여백 설정
		gbc.gridx = 0; // 그리드 x축 위치
		gbc.gridy = 0; // 그리드 y축 위치
		gbc.anchor = GridBagConstraints.CENTER; // 앵커 설정
		return gbc; // 그리드백 설정 반환
	}

	private void addBackground(JPanel backgroundPanel, GridBagConstraints gbc) { // 배경 패널에 컴포넌트 추가 메서드
		JLabel titleLabel = createTitle(); // 타이틀 레이블 생성
		backgroundPanel.add(titleLabel, gbc); // 배경 패널에 타이틀 레이블 추가
		gbc.gridy++; // y축 증가
		addButton(backgroundPanel, easyButton, gbc); // 배경 패널에 이지 버튼 추가
		gbc.gridy++; // y축 증가
		addButton(backgroundPanel, mediumButton, gbc); // 배경 패널에 미디엄 버튼 추가
		gbc.gridy++; // y축 증가
		addButton(backgroundPanel, hardButton, gbc); // 배경 패널에 하드 버튼 추가
		gbc.gridy++; // y축 증가
		addButton(backgroundPanel, customButton, gbc); // 배경 패널에 커스텀 버튼 추가
		gbc.gridy++; // y축 증가
		addButton(backgroundPanel, timeAttackButton, gbc); // 배경 패널에 타임어택 버튼 추가
		gbc.gridy++; // y축 증가
		addButton(backgroundPanel, moveLimitButton, gbc); // 배경 패널에 움직임 제한 모드 버튼 추가
		gbc.gridy++; // y축 증가
		addButton(backgroundPanel, impossibleButton, gbc); // 배경 패널에 임파서블 모드 버튼 추가
		gbc.gridy++; // y축 증가
		addButton(backgroundPanel, rankingButton, gbc); // 배경 패널에 랭킹 버튼 추가

	}

	private JLabel createTitle() { // 타이틀 레이블 생성 메서드
		JLabel titleLabel = new JLabel("게임 시작"); // 타이틀 레이블 생성
		titleLabel.setFont(new Font("궁서 보통", Font.BOLD, 24)); // 폰트 설정
		titleLabel.setForeground(Color.WHITE); // 전경색 설정
		return titleLabel; // 타이틀 레이블 반환
	}

	private void addButton(JPanel panel, JButton button, GridBagConstraints gbc) { // 패널에 버튼 추가 메서드
		setButton(button); // 버튼 스타일링
		panel.add(button, gbc); // 패널에 버튼 추가
	}

	private void setButton(JButton button) { // 버튼 스타일링 메서드
		button.setFont(new Font("궁서 보통", Font.PLAIN, 18)); // 폰트 설정
		button.setBackground(new Color(30, 144, 255)); // 배경색 설정
		button.setForeground(Color.WHITE); // 전경색 설정
		button.setFocusPainted(false); // 포커스 그리기 비활성화
		button.setBorderPainted(false); // 테두리 그리기 비활성화
		button.setOpaque(true); // 투명도 설정
	}

	public void setEasyButtonListener(ActionListener listener) { // 이지 버튼 리스너 설정 메서드
		easyButton.addActionListener(listener); // 이지 버튼에 리스너 추가
	}

	public void setMediumButtonListener(ActionListener listener) { // 미디엄 버튼 리스너 설정 메서드
		mediumButton.addActionListener(listener); // 미디엄 버튼에 리스너 추가
	}

	public void setHardButtonListener(ActionListener listener) { // 하드 버튼 리스너 설정 메서드
		hardButton.addActionListener(listener); // 하드 버튼에 리스너 추가
	}

	public void setCustomButtonListener(ActionListener listener) { // 커스텀 버튼 리스너 설정 메서드
		customButton.addActionListener(listener); // 커스텀 버튼에 리스너 추가
	}

	public void setRankingButtonListener(ActionListener listener) { // 랭킹 버튼 리스너 설정 메서드
		rankingButton.addActionListener(listener); // 랭킹 버튼에 리스너 추가
	}

	public void setTimeAttackButtonListener(ActionListener listener) { // 타임어택 버튼 리스너 설정 메서드
		timeAttackButton.addActionListener(listener); // 타임어택 버튼에 리스너 추가
	}

	public void setMoveLimitButtonListener(ActionListener listener) { // 움직임 제한 모드 버튼 리스너 설정 메서드
		moveLimitButton.addActionListener(listener); // 움직임 제한 모드 버튼에 리스너 추가
	}

	public void setImpossibleButtonListener(ActionListener listener) { // 임파서블 모드 버튼 리스너 설정 메서드
		impossibleButton.addActionListener(listener); // 임파서블 모드 버튼에 리스너 추가
	}
}
