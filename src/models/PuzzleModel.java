package models; // 패키지 models 선언

import java.awt.Image; // AWT Image import
import java.util.*; // 유틸리티 패키지 import

public class PuzzleModel { // PuzzleModel 클래스 선언
    private Tile[] tiles; // 타일 배열 선언
    private int size; // 퍼즐 크기 변수 선언
    private int emptyTileIndex; // 빈 타일 인덱스 변수 선언
    private int moveCount; // 이동 횟수 변수 선언

    public PuzzleModel(int size) { // 생성자 정의
        this.size = size; // 퍼즐 크기 설정
        this.tiles = new Tile[size * size]; // 타일 배열 초기화
        initializeTiles(); // 타일 초기화 메서드 호출
        this.moveCount = 0; // 이동 횟수 초기화
    }

    private void initializeTiles() { // 타일 초기화 메서드 정의
        for (int i = 0; i < size * size - 1; i++) { // 모든 타일에 숫자 퍼즐로 초기화
            tiles[i] = new Tile(i + 1, null); 
        }
        tiles[size * size - 1] = new Tile(0, null); // 마지막 타일은 빈 타일
        emptyTileIndex = size * size - 1; // 빈 타일 인덱스 설정
    }

    public void setTiles(Tile[] tiles) { // 타일 설정 메서드 정의
        this.tiles = tiles; // 타일 배열 설정
        for (int i = 0; i < tiles.length; i++) { // 빈 타일 인덱스 찾기
            if (tiles[i].getNumber() == 0) {
                emptyTileIndex = i; // 빈 타일 인덱스 설정
                break;
            }
        }
    }

    public void shuffle() { // 퍼즐 섞기 메서드 정의
        List<Tile> tileList = Arrays.asList(tiles); // 타일 리스트로 변환
        Collections.shuffle(tileList); // 리스트 섞기
        tiles = tileList.toArray(new Tile[0]); // 다시 배열로 변환

        for (int i = 0; i < tiles.length; i++) { // 빈 타일 인덱스 찾기
            if (tiles[i].getNumber() == 0) {
                emptyTileIndex = i; // 빈 타일 인덱스 설정
                break;
            }
        }
        moveCount = 0; // 섞을 때 이동 횟수 초기화
    }

    public boolean moveTile(int index) { // 타일 이동 메서드 정의
        if (isValidMove(index)) { // 이동 가능한지 확인
            Tile temp = tiles[emptyTileIndex]; // 임시 변수에 빈 타일 저장
            tiles[emptyTileIndex] = tiles[index]; // 빈 타일에 선택한 타일 저장
            tiles[index] = temp; // 선택한 타일에 빈 타일 저장
            emptyTileIndex = index; // 빈 타일 인덱스 업데이트
            moveCount++; // 이동 횟수 증가
            return true; // 이동 성공 반환
        }
        return false; // 이동 실패 반환
    }

    private boolean isValidMove(int index) { // 이동 가능 여부 확인 메서드 정의
        int row = index / size; // 선택한 타일의 행
        int col = index % size; // 선택한 타일의 열
        int emptyRow = emptyTileIndex / size; // 빈 타일의 행
        int emptyCol = emptyTileIndex % size; // 빈 타일의 열

        return (Math.abs(row - emptyRow) == 1 && col == emptyCol) || // 상하로 이동 가능한 경우
               (Math.abs(col - emptyCol) == 1 && row == emptyRow); // 좌우로 이동 가능한 경우
    }

    public boolean isSolved() { // 퍼즐이 완성되었는지 확인하는 메서드 정의
        for (int i = 0; i < size * size - 1; i++) { // 모든 타일 순회
            if (tiles[i].getNumber() != i + 1) { // 타일 번호가 순서대로가 아닌 경우
                return false; // 퍼즐 미완성 반환
            }
        }
        return tiles[size * size - 1].getNumber() == 0; // 마지막 타일이 빈 타일인 경우 퍼즐 완성 반환
    }

    public Tile[] getTiles() { // 타일 배열 반환 메서드 정의
        return tiles; // 타일 배열 반환
    }

    public int getSize() { // 퍼즐 크기 반환 메서드 정의
        return size; // 퍼즐 크기 반환
    }

    public int getMoveCount() { // 이동 횟수 반환 메서드 정의
        return moveCount; // 이동 횟수 반환
    }
}
