package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {

    private CoffeeRepository coffeeRepository;

    public List<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee index(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    public Coffee create(CoffeeDto dto) {
        Coffee coffee = dto.toEntity();
        if (coffee.getId() != null) {
            return null;
        }
        return coffeeRepository.save(coffee);
    }

    public Coffee update(Long id, CoffeeDto dto) {
        Coffee coffee = dto.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target == null || id != coffee.getId()) {
            return null;
        }
        target.patch(coffee);
        Coffee updated = coffeeRepository.save(target);

        return updated;
    }

    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        coffeeRepository.delete(target);
        return target;
    }
}

/*
1. 서비스
- 컨트롤러와 리파지터리 사이에 위치하는 계층
- 서버의 핵심 기능(비지니스 로직)을 처리히는 순서를 총괄한다.
- 클라이언트가 요청을 보내면 이를 컨트롤러가 받아 서비스로전달하고,
  서비스는 정해진 코드흐름을 따라 처리를 진행한다.
- 이 때 처리에 필요한 데이터는 리파지터리가 DB에서 가져와 서비스로 반환한다.
-> 이렇게 대부분의 웹 서비스는 컨트롤러와 리파지터리 사이에 서비스 계층을 두어 역할을 분업한다.

2. 트랜잭션
- 모두 성공해야 하는 일련의 과정
- 쪼갤 수 없는 업무 처리의 최소 단위
- 보통 트랜잭션은 서비스단에서 관리한다.

3. 롤백
- 트랜잭션 내부에서 실행에 실패하면 지금까지 수행한 것을 모두 폐기하고
  진행 초기 단계로 되돌리는데, 이를 롤백이라고 한다.

4. @Service
- 해당 어노테이션이 선언된 클래스를 서비스로 인식해 서비스 객체를 생성한다.
- 컨트롤러는 객체 주입하는 방식으로 서비스 객체를 상ㅇ할 수 있다.

5. @Transactional
- 해당 어노테이션이 선언된 메서드를 트랜잭션으로 묵는다.
- 클래스에 이 어노테이션을 선ㅇ너하면 클래스의 모든 메서드별로 각각의
  트랜잭션이 부여된다.
 */