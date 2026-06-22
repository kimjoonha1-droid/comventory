package com.oracle.comventory.service.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardPaging {
    private int currentPage = 1;  // 현재 페이지 번호 (기본값 1)
    private int rowPage     = 20; // 한 페이지에 보여줄 게시글 수
    private int pageBlock   = 10; // 한 번에 보여줄 페이지 버튼 수 (1~10, 11~20 등)
    private int start;            // 현재 페이지의 시작 게시글 번호
    private int end;              // 현재 페이지의 끝 게시글 번호
    private int startPage;        // 현재 페이지 블록의 시작 페이지 번호
    private int endPage;          // 현재 페이지 블록의 끝 페이지 번호
    private int total;            // 전체 게시글 수
    private int totalPage;        // 전체 페이지 수

    public BoardPaging(int total, String currentPage1) {
        this.total = total;    // 전체 게시글 수 설정
        if (currentPage1 != null && !currentPage1.isEmpty()) {
            this.currentPage = Integer.parseInt(currentPage1);
        }
        // 현재 페이지의 시작/끝 게시글 번호 계산
        start     = (currentPage - 1) * rowPage + 1;  // 1페이지=1, 2페이지=11, 3페이지=21
        end       = start + rowPage - 1;               // 1페이지=10, 2페이지=20, 3페이지=30

        // 전체 페이지 수 계산 (소수점 올림)
        totalPage = (int) Math.ceil((double) total / rowPage);  // 25개면 3페이지

        // 페이지 블록 시작/끝 번호 계산
        startPage = currentPage - (currentPage - 1) % pageBlock;  // 1~10페이지면 1
        endPage   = startPage + pageBlock - 1;                     // 1~10페이지면 10

        // 끝 페이지가 전체 페이지 수를 넘으면 전체 페이지 수로 설정
        if (endPage > totalPage) {
            endPage = totalPage;
        }
    }
}