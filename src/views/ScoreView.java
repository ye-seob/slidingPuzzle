package views; // View 패키지 선언

import java.awt.*; // AWT 패키지 import
import java.net.URL; // URL import
import java.util.List; // List import

import javax.swing.*; // Swing 패키지 import
import javax.swing.table.*; // 테이블 패키지 import

import controllers.ScoreController; // ScoreController import
import models.Score; // Score import

public class ScoreView extends JPanel { // ScoreView 클래스 선언
    private JTable scoreTable; // 점수 테이블 선언
    private String[] score = {"Name", "Time", "Moves"}; // 컬럼 이름 배열 선언
    private JButton backButton = new JButton("뒤로 가기"); // 뒤로 가기 버튼 선언
    private Image backgroundImg; // 배경 이미지 선언
    private ScoreController controller; // ScoreController 선언

    public ScoreView(List<Score> scores) { // 생성자 정의
        setLayout(new BorderLayout()); // 레이아웃 설정
        loadBackgroundImage(); // 배경 이미지 로드
        
        Object[][] data = new Object[scores.size()][3]; // 데이터 배열 초기화
        for (int i = 0; i < scores.size(); i++) { // 점수 리스트를 반복하여 데이터 배열에 추가
            Score score = scores.get(i); // 현재 점수 가져오기
            data[i][0] = score.getName(); // 이름 추가
            data[i][1] = score.getTime(); // 시간 추가
            data[i][2] = score.getMoves(); // 움직임 추가
        }

        scoreTable = new JTable(data, score) { // 점수 테이블 생성
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) { // 렌더러 설정 메서드 재정의
                Component Comp = super.prepareRenderer(renderer, row, column); // 상위 클래스 호출
                if (Comp instanceof JComponent) { // 컴포넌트가 JComponent일 때
                    ((JComponent) Comp).setOpaque(false); // 투명 설정
                }
                return Comp; // 컴포넌트 반환
            }
        };
        styleTable(scoreTable); // 테이블 스타일링

        JScrollPane scrollPanel = new JScrollPane(scoreTable); // 스크롤 패널 생성
        scrollPanel.setOpaque(false); // 스크롤 패널 투명 설정
        scrollPanel.getViewport().setOpaque(false); // 스크롤 패널 뷰포트 투명 설정

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)) { // 하단 패널 생성
            @Override
            protected void paintComponent(Graphics g) { // 그래픽 요소 그리기 메서드 재정의
                super.paintComponent(g); // 상위 클래스 호출
                g.setColor(new Color(45, 45, 45, 150)); // 색상 설정
                g.fillRect(0, 0, getWidth(), getHeight()); // 사각형 그리기
            }
        };
        bottomPanel.setOpaque(false); // 하단 패널 투명 설정
        bottomPanel.add(backButton); // 하단 패널에 뒤로 가기 버튼 추가

        add(scrollPanel, BorderLayout.CENTER); // 스크롤 패널을 중앙에 추가
        add(bottomPanel, BorderLayout.SOUTH); // 하단 패널을 남쪽에 추가

        backButton.addActionListener(e -> controller.backButtonClicked()); // 뒤로 가기 버튼 리스너 설정
    }

    public void setController(ScoreController controller) { // 컨트롤러 설정 메서드
        this.controller = controller; // 컨트롤러 설정
    }

    private void styleTable(JTable table) { // 테이블 스타일링 메서드
        table.setFont(new Font("궁서 보통", Font.BOLD, 16)); // 폰트 설정
        table.setForeground(Color.WHITE); // 전경색 설정
        table.setBackground(new Color(0, 0, 0, 150)); // 배경색 설정
        table.setGridColor(Color.GRAY); // 그리드 색상 설정
        table.setShowGrid(false); // 그리드 표시 여부 설정
        table.setIntercellSpacing(new Dimension(0, 0)); // 셀 간격 설정

        JTableHeader header = table.getTableHeader(); // 테이블 헤더 가져오기
        header.setFont(new Font("궁서 보통", Font.BOLD, 18)); // 폰트 설정
        header.setForeground(Color.WHITE); // 전경색 설정
        header.setBackground(new Color(30, 144, 255)); // 배경색 설정
        header.setOpaque(false); // 투명 설정

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(); // 기본 셀 렌더러 생성
        renderer.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬 설정
        table.setDefaultRenderer(Object.class, renderer); // 기본 렌더러 설정
    }

    private void loadBackgroundImage() { // 배경 이미지 로드 메서드
        try { // 예외 처리
            URL imageUrl = getClass().getClassLoader().getResource("images/하늘 배경.png"); // 이미지 URL 가져오기
            if (imageUrl != null) { // 이미지가 null이 아닌 경우
                backgroundImg = new ImageIcon(imageUrl).getImage(); // 배경 이미지 설정
            } else { // 이미지가 null인 경우
                System.err.println("Background image not found"); // 에러 메시지 출력
            }
        } catch (Exception e) { // 예외 처리
            e.printStackTrace(); // 에러 출력
        }
    }

    @Override
    protected void paintComponent(Graphics g) { // 그래픽 요소 그리기 메서드 재정의
        super.paintComponent(g); // 상위 클래스 호출
        if (backgroundImg != null) { // 배경 이미지가 null이 아닌 경우
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 그리기
        }
    }
}
