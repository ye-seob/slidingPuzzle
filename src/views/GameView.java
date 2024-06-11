package views; // 패키지 views 선언

import javax.imageio.ImageIO; // 이미지 입출력 관련 패키지 import
import javax.swing.*; // Swing 패키지 import
import java.awt.*; // AWT 패키지 import
import java.awt.image.BufferedImage; // 이미지 버퍼링 관련 패키지 import
import java.net.URL; // URL 관련 패키지 import
import controllers.GameController; // GameController 클래스 import
import models.Tile; // Tile 클래스 import

public class GameView extends JPanel { // GameView 클래스 선언
    private final JButton[] button; // 버튼 배열 선언
    private final int size; // 퍼즐 크기 변수 선언
    
    private final JLabel timer = new JLabel("시간: 0"); // 타이머 레이블 선언
    private final JLabel move = new JLabel("이동 횟수: 0"); // 이동 횟수 레이블 선언
    private final JButton backButton = new JButton("뒤로 가기"); // 뒤로 가기 버튼 선언
    private final JLabel tileInitTimer = new JLabel(); // 셔플 타이머 레이블 선언
    
    private boolean isImpossibleMode; // 불가능 모드 여부 변수 선언
    private boolean isMoveLimitMode; // 이동 제한 모드 여부 변수 선언
    
    private Image backgroundImg; // 배경 이미지 변수 선언
    private ImageIcon tile; // 기본 타일 아이콘 변수 선언
    private ImageIcon[] customTile; // 사용자 정의 타일 아이콘 배열 변수 선언

    public GameView(int size) { // 생성자 정의
        this.size = size; // 퍼즐 크기 설정
        this.button = new JButton[size * size]; // 버튼 배열 초기화
        
        setLayout(new BorderLayout()); // 레이아웃 설정
        
        loadBackgroundImg(); // 배경 이미지 로드
        loadTile(); // 기본 타일 아이콘 로드

        JPanel grid = new JPanel(new GridLayout(size, size, 5, 5)) { // 그리드 패널 생성
            @Override
            protected void paintComponent(Graphics g) { // 그리기 컴포넌트 재정의
                super.paintComponent(g); // 부모 클래스 호출
                g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 그리기
            }
        };
        initButton(grid); // 버튼 초기화
        add(grid, BorderLayout.CENTER); // 그리드 패널을 중앙에 추가

        JPanel topPanel = new JPanel(new BorderLayout()); // 상단 패널 생성
        topPanel.setOpaque(false); // 투명 설정

        move.setForeground(Color.WHITE); // 이동 횟수 레이블 텍스트 색상 설정
        move.setFont(new Font("궁서 보통", Font.BOLD, 16)); // 이동 횟수 레이블 폰트 설정

        tileInitTimer.setForeground(Color.WHITE); // 셔플 타이머 레이블 텍스트 색상 설정
        tileInitTimer.setFont(new Font("궁서 보통", Font.BOLD, 16)); // 셔플 타이머 레이블 폰트 설정
        tileInitTimer.setVisible(false); // 초기에는 보이지 않음

        timer.setForeground(Color.WHITE); // 타이머 레이블 텍스트 색상 설정
        timer.setFont(new Font("궁서 보통", Font.BOLD, 16)); // 타이머 레이블 폰트 설정

        JPanel topRightPanel = new JPanel(new BorderLayout()); // 우측 상단 패널 생성
        topRightPanel.setOpaque(false); // 투명 설정
        backButton.setPreferredSize(new Dimension(100, 30)); // 뒤로 가기 버튼 크기 설정
        topRightPanel.add(backButton, BorderLayout.EAST); // 뒤로 가기 버튼을 우측에 추가

        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 좌측 상단 패널 생성
        topLeftPanel.setOpaque(false); // 투명 설정
        topLeftPanel.add(timer); // 타이머 레이블 추가
        topLeftPanel.add(tileInitTimer); // 셔플 타이머 레이블 추가
        topLeftPanel.add(move); // 이동 횟수 레이블 추가

        topPanel.add(topRightPanel, BorderLayout.EAST); // 우측 상단 패널을 우측에 추가
        topPanel.add(topLeftPanel, BorderLayout.WEST); // 좌측 상단 패널을 좌측에 추가

        add(topPanel, BorderLayout.NORTH); // 상단 패널을 상단에 추가
    }

    public void initButton(JPanel panel) { // 버튼 초기화 메서드 정의
        for (int i = 0; i < size * size; i++) { // 버튼 배열 크기만큼 반복
            button[i] = new JButton(); // 새로운 버튼 생성
            customizeButton(button[i]); // 버튼 스타일링
            panel.add(button[i]); // 패널에 버튼 추가
        }
    }

    private void customizeButton(JButton button) { // 버튼 스타일링 메서드 정의
        button.setFont(new Font("궁서 보통", Font.BOLD, 40)); // 폰트 설정
        button.setContentAreaFilled(false); // 내용 영역 채우기 설정
        button.setBorderPainted(false); // 테두리 그리기 설정
        button.setFocusPainted(false); // 포커스 그리기 설정
        button.setForeground(Color.WHITE); // 전경색 설정
        button.setIcon(tile); // 아이콘 설정
        button.setHorizontalTextPosition(JButton.CENTER); // 텍스트 수평 위치 설정
        button.setVerticalTextPosition(JButton.CENTER); // 텍스트 수직 위치 설정
    }

    public void updateView(Tile[] tiles) { // 뷰 업데이트 메서드 정의
        for (int i = 0; i < tiles.length; i++) { // 타일 배열 순회
            if (tiles[i].getNumber() == 0) { // 타일이 빈 타일인 경우
                button[i].setText(""); // 텍스트 없음
                button[i].setIcon(tile); // 기본 타일 아이콘 설정
            } else { // 빈 타일이 아닌 경우
                button[i].setText(customTile != null ? "" : String.valueOf(tiles[i].getNumber())); // 텍스트 설정
                button[i].setIcon(customTile != null ? customTile[tiles[i].getNumber() - 1] : tile); // 아이콘 설정
            }
        }
    }

    public void setController(GameController controller) { // 컨트롤러 설정 메서드 정의
        for (int i = 0; i < button.length; i++) { // 버튼 배열 순회
            int index = i; // 인덱스 설정
            button[i].addActionListener(e -> controller.tileClick(index)); // 타일 클릭 리스너 설정
        }
        backButton.addActionListener(e -> controller.backButtonClicked()); // 뒤로 가기 버튼 리스너 설정
    }

    public void setImpossibleMode(boolean isImpossibleMode) { // 불가능 모드 설정 메서드 정의
        this.isImpossibleMode = isImpossibleMode; // 불가능 모드 설정
        timer.setVisible(!isImpossibleMode); // 타이머 레이블 표시 여부 설정
        tileInitTimer.setVisible(isImpossibleMode); // 셔플 타이머 레이블 표시 여부 설정
        revalidate(); // 다시 유효성 검사
        repaint(); // 다시 그리기
    }

    public void setMoveLimitMode(boolean isMoveLimitMode) { // 이동 제한 모드 설정 메서드 정의
        this.isMoveLimitMode = isMoveLimitMode; // 이동 제한 모드 설정
        if (isMoveLimitMode) { // 이동 제한 모드일 경우
            move.setText("남은 이동 횟수: 30"); // 이동 횟수 레이블 텍스트 설정
        } else { // 이동 제한 모드가 아닐 경우
            move.setText("이동 횟수: 0"); // 이동 횟수 레이블 텍스트 설정
        }
        revalidate(); // 다시 유효성 검사
        repaint(); // 다시 그리기
    }

    public void updateTimer(int seconds) { // 타이머 업데이트 메서드 정의
        if (!isImpossibleMode) { // 불가능 모드가 아닐 경우
            timer.setText("시간: " + seconds); // 타이머 레이블 텍스트 설정
        }
    }

    public void updateMoveCount(int moveCount) { // 이동 횟수 업데이트 메서드 정의
        if (isMoveLimitMode) { // 이동 제한 모드일 경우
            move.setText("남은 이동 횟수: " + moveCount); // 이동 횟수 레이블 텍스트 설정
        } else { // 이동 제한 모드가 아닐 경우
            move.setText("이동 횟수: " + moveCount); // 이동 횟수 레이블 텍스트 설정
        }
    }

    public void updateInitTimer(int seconds) { // 셔플 타이머 업데이트 메서드 정의
        if (isImpossibleMode) { // 불가능 모드일 경우
            tileInitTimer.setText("초기화까지: " + seconds + "초"); // 셔플 타이머 레이블 텍스트 설정
        }
    }

    private void loadBackgroundImg() { // 배경 이미지 로드 메서드 정의
        try { // 예외 처리
            URL imageUrl = getClass().getClassLoader().getResource("images/하늘 배경.png"); // 이미지 파일 URL
            if (imageUrl != null) { // URL이 null이 아닐 경우
                backgroundImg = new ImageIcon(imageUrl).getImage(); // 배경 이미지 로드
            } else { // URL이 null일 경우
                System.err.println("Background image not found"); // 에러 메시지 출력
            }
        } catch (Exception e) { // 예외 발생 시
            e.printStackTrace(); // 스택 트레이스 출력
        }
    }

    private void loadTile() { // 기본 타일 아이콘 로드 메서드 정의
        try { // 예외 처리
            URL imageUrl = getClass().getClassLoader().getResource("images/blue_tile.png"); // 타일 이미지 파일 URL
            if (imageUrl != null) { // URL이 null이 아닐 경우
                BufferedImage img = ImageIO.read(imageUrl); // 이미지 로드
                int tileSize = 240; // 타일 크기
                Image resizedImg = img.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH); // 이미지 크기 조정
                BufferedImage transparentImg = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB); // 투명 이미지 생성
                Graphics2D g2d = transparentImg.createGraphics(); // 그래픽 객체 생성
                float alpha = 0.5f; // 투명도
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha); // 투명도 조절
                g2d.setComposite(alphaComposite); // 투명도 적용
                g2d.drawImage(resizedImg, 0, 0, null); // 이미지 그리기
                g2d.dispose(); // 그래픽 객체 해제
                tile = new ImageIcon(transparentImg); // 기본 타일 아이콘 설정
            } else { // URL이 null일 경우
                System.err.println("Tile image not found"); // 에러 메시지 출력
            }
        } catch (Exception e) { // 예외 발생 시
            e.printStackTrace(); // 스택 트레이스 출력
        }
    }

    public void setCustomTileIcons(ImageIcon[] customTileIcons) { // 사용자 정의 타일 아이콘 설정 메서드 정의
        this.customTile = customTileIcons; // 사용자 정의 타일 아이콘 배열 설정
    }

    @Override
    protected void paintComponent(Graphics g) { // 그리기 컴포넌트 재정의
        super.paintComponent(g); // 부모 클래스 호출
        g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this); // 배경 이미지 그리기
    }
}
