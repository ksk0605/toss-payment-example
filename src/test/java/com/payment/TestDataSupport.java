package com.payment;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.payment.billing.domain.Plan;
import com.payment.billing.domain.PlanRepository;
import com.payment.user.domain.User;
import com.payment.user.domain.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class TestDataSupport {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    protected User user;
    protected Plan plan;

    @BeforeEach
    public void setUp() {
        user = createUser("test@test.com", "test");
        plan = createPlan("test", 1000);
    }

    public User createUser(String email, String name) {
        User user = User.builder()
                .email(email)
                .name(name)
                .build();
        return userRepository.save(user);
    }

    public Plan createPlan(String name, int price) {
        Plan plan = Plan.builder()
                .name(name)
                .price(price)
                .build();
        return planRepository.save(plan);
    }
}
