package com.cyes.webserver.domain.problem.service;

import com.cyes.webserver.domain.problem.dto.request.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.request.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.problem.entity.*;
import com.cyes.webserver.domain.problem.repository.ProblemRepository;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {
    private final ProblemRepository problemRepository;

    //객관식 문제 저장
    @Transactional
    public ProblemResponse saveMultipleChoice(MultipleChoiceProblemSaveRequest multipleChoiceProblemSaveRequest){
        log.info("multipleChoiceProblemSaveRequest = {}",multipleChoiceProblemSaveRequest);
        //Dto -> Document
        Problem multipleChoice = Problem.createMultipleChoice(multipleChoiceProblemSaveRequest);

        //Problem DB 저장
        problemRepository.save(multipleChoice);

        //Entity -> Dto
        ProblemResponse problemResponse = multipleChoice.toProblemResponse(0);

        return problemResponse;
    }
    //단답형 문제 저장
    @Transactional
    public ProblemResponse saveShortAnswer(ShortAnswerProblemSaveRequest shortAnswerProblemSaveRequest){
        log.info("shortAnswerProblemSaveRequest = {}",shortAnswerProblemSaveRequest);
        //Dto -> Entity
        Problem shortAnswer = Problem.createShortAnswer(shortAnswerProblemSaveRequest);

        //Problem DB 저장
        problemRepository.save(shortAnswer);

        //Entity -> Dto
        ProblemResponse problemResponse = shortAnswer.toProblemResponse(0);
        return problemResponse;
    }
    //오엑스 문제 저장
    @Transactional
    public ProblemResponse saveTrueOrFalse(TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest){
        log.info("trueOrFalseProblemSaveRequest = {}",trueOrFalseProblemSaveRequest);
        //Dto -> Entity
        Problem trueOrFalse = Problem.createTrueOrFalse(trueOrFalseProblemSaveRequest);

        //Problem DB 저장
        problemRepository.save(trueOrFalse);

        //Entity -> Dto
        ProblemResponse problemResponse = trueOrFalse.toProblemResponse(0);
        return problemResponse;
    }
    //문제 전체 조회 - 페이지네이션
    public Page<ProblemResponse> findAllProblem(Pageable pageable){

        //페이지네이션 적용해서 select
        Page<Problem> problemPage = problemRepository.findAll(pageable);

        //getContent 이용해서 문제 리스트 가져옴
        List<Problem> problemList = problemPage.getContent();

        //problemListResponseList로 변환
        List<ProblemResponse> problemResponseList = toProblemResponseList(problemList);

        return new PageImpl<>(problemResponseList, problemPage.getPageable(), problemPage.getTotalElements());
    }

    //catetory,type으로 문제 조회 - 페이지네이션
    public Page<ProblemResponse> findProblemByCategoryOrType(ProblemCategory problemCategory, ProblemType problemType, Pageable pageable) {
        Page<Problem> problemPage = null;
        //선택조건이 하나도 없다면
        if (problemCategory == null && problemType == null) {
            throw new CustomException(CustomExceptionList.CATEGORY_OR_TYPE_MUST_REQUIRED);
        } else if (problemCategory != null && problemType != null) {
            problemPage = problemRepository.findProblemByCategoryAndType(problemCategory, problemType, pageable);
        } else if (problemType != null) {
            problemPage = problemRepository.findProblemByType(problemType, pageable);
        } else {
            problemPage = problemRepository.findProblemByCategory(problemCategory, pageable);
        }

        //getContent 이용해서 문제 리스트 가져옴
        List<Problem> problemList = problemPage.getContent();

        //problemListResponseList로 변환
        List<ProblemResponse> problemResponseList = toProblemResponseList(problemList);

        return new PageImpl<>(problemResponseList, problemPage.getPageable(), problemPage.getTotalElements());
    }

    /*
    * 특정 퀴즈의 문제pk를 list로 넘겨주면
    * 그에 해당하는 problem들을 반환한다.
    * */
     public List<ProblemResponse> findAllProblemByQuiz(List<String> problemIdList){
         Iterable<Problem> findProblems = problemRepository.findAllById(problemIdList);
         List<ProblemResponse> problemResponseList = toProblemResponseList(findProblems);
         return problemResponseList;
     }


    //문제를 삭제한다.
    @Transactional
    public void deleteProblem(String id){
        problemRepository.deleteById(id);
    }

    //문제 랜덤으로 정해진 갯수만큼 조회
    //문제 갯수, 카테고리, 입력받아서 랜덤으로 조회
    private List<ProblemResponse> toProblemResponseList(Iterable<Problem> problemList){
        List<ProblemResponse> list = new ArrayList<>();
        int problemOrder = 1;
        for (Problem problem : problemList) {
            list.add(problem.toProblemResponse(problemOrder++));
        }
        return list;
    }
}
