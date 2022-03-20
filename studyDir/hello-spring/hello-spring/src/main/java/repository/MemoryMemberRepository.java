package repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //시퀀스 시스템이 정해주는 회원 고유 ID를 정해줌
        store.put(member.getId(), member);  // //store에 시스템이 정해주는 고유 ID를 넣음
        return member; // 반환
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // Optional.ofNullable null이 반환되지 않게
    }

    @Override
    public Optional<Member> findByName(String name) {
        //member.getName()이랑 파라미터로 넘어온 name이랑 같은지 비교  (.findAny : 검색을 돌다가 검색값이 하나가 발견되면 바로 그 값을 반환하고 끝)
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void cleanStore() {
        store.clear();
    }
}
