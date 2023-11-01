package com.cyes.webserver.domain.problem.service;

import com.cyes.webserver.domain.problem.dto.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.dto.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.entity.*;
import com.cyes.webserver.domain.problem.repository.MultipleChoiceRepository;
import com.cyes.webserver.domain.problem.repository.ProblemRepository;
import com.cyes.webserver.domain.problem.repository.ShortAnswerRepository;
import com.cyes.webserver.domain.problem.repository.TrueOrFalseRepository;
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
    private final MultipleChoiceRepository multipleChoiceRepository;
    private final ShortAnswerRepository shortAnswerRepository;
    private final TrueOrFalseRepository trueOrFalseRepository;


    //객관식 문제 저장
    @Transactional
    public ProblemResponse saveMultipleChoice(MultipleChoiceProblemSaveRequest multipleChoiceProblemSaveRequest){
        log.info("multipleChoiceProblemSaveRequest = {}",multipleChoiceProblemSaveRequest);

        //Dto -> Entity
        Problem problem = multipleChoiceProblemSaveRequest.toEntity();

        //Content DB 저장
        multipleChoiceRepository.save((MultipleChoice) problem.getContent());

        //Problem DB 저장
        problemRepository.save(problem);

        //Entity -> Dto
        ProblemResponse problemResponse = problem.toProblemResponse();

        return problemResponse;
    }
    //단답형 문제 저장
    @Transactional
    public ProblemResponse saveShortAnswer(ShortAnswerProblemSaveRequest shortAnswerProblemSaveRequest){
        log.info("shortAnswerProblemSaveRequest = {}",shortAnswerProblemSaveRequest);

        //Dto -> Entity
        Problem problem = shortAnswerProblemSaveRequest.toEntity();

        //Content DB 저장
        shortAnswerRepository.save((ShortAnswer) problem.getContent());

        //Problem DB 저장
        problemRepository.save(problem);

        //Entity -> Dto
        ProblemResponse problemResponse = problem.toProblemResponse();
        return problemResponse;
    }
    //오엑스 문제 저장
    @Transactional
    public ProblemResponse saveTrueOrFalse(TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest){
        log.info("trueOrFalseProblemSaveRequest = {}",trueOrFalseProblemSaveRequest);

        //Dto -> Entity
        Problem problem = trueOrFalseProblemSaveRequest.toEntity();

        //Content DB 저장
        trueOrFalseRepository.save((TrueOrFalse) problem.getContent());

        //Problem DB 저장
        problemRepository.save(problem);

        //Entity -> Dto
        ProblemResponse problemResponse = problem.toProblemResponse();
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

    //문제 수정하는 메서드
//    @Transactional
//    public ProblemResponse updateProblem(ProblemUpdateServiceRequest problemUpdateServiceRequest){
//        //Problem id 값
//        String id = problemUpdateServiceRequest.getId();
//
//        //id값으로 문제를 먼저 찾는다.
//        //존재하지 않으면 예외 발생
//        Problem findProblem = problemRepository.findById(id).orElseThrow(() -> {
//            throw new CustomException(CustomExceptionList.PROBLEM_NOT_FOUND_ERROR);
//        });
//        //Entity 수정
//        findProblem.changeByUpdateDto(problemUpdateServiceRequest);
//
//        //Entity -> Dto
//        return findProblem.toProblemResponse();
//    }

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
