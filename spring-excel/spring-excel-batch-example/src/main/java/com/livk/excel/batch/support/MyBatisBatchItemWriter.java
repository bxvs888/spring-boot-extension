package com.livk.excel.batch.support;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * <p>
 * MybatisBatchWrite
 * </p>
 *
 * @author livk
 * @date 2022/8/17
 */
@RequiredArgsConstructor
public class MyBatisBatchItemWriter<T> implements ItemWriter<T> {

    private final SqlSessionFactory sqlSessionFactory;

    private final String statementId;

    @Override
    public void write(List<? extends T> items) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        try {
            for (T item : items) {
                sqlSession.update(statementId, item);
            }
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }
        sqlSession.close();
    }
}