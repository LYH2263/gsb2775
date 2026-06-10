package com.label2775.resume.config;

import com.label2775.resume.domain.Education;
import com.label2775.resume.domain.Experience;
import com.label2775.resume.domain.Profile;
import com.label2775.resume.domain.Project;
import com.label2775.resume.domain.Skill;
import com.label2775.resume.repository.EducationRepository;
import com.label2775.resume.repository.ExperienceRepository;
import com.label2775.resume.repository.ProfileRepository;
import com.label2775.resume.repository.ProjectRepository;
import com.label2775.resume.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    @Bean
    @Transactional
    public CommandLineRunner seedData(ProfileRepository profileRepository,
                                      EducationRepository educationRepository,
                                      ExperienceRepository experienceRepository,
                                      ProjectRepository projectRepository,
                                      SkillRepository skillRepository) {
        return args -> {
            if (profileRepository.count() > 0) {
                log.info("Seed data already exists, skip seeding.");
                return;
            }

            log.info("Seeding initial resume data...");

            Profile profile = new Profile();
            profile.setName("张三");
            profile.setTitle("高级 Java 开发工程师");
            profile.setLocation("上海");
            profile.setEmail("zhangsan@example.com");
            profile.setPhone("+86-13800000000");
            profile.setGithubUrl("https://github.com/zhangsan");
            profile.setWebsiteUrl("https://zhangsan.dev");
            profile.setSummary("拥有 7+ 年后端与全栈开发经验，长期负责核心交易与结算系统的架构与落地，对高并发、高可用、可观测性以及工程效率有系统化实践经验。熟悉从需求分析、方案设计、编码实现、自动化测试到上线运维的完整生命周期，热衷于通过工程化手段提升团队交付质量与稳定性。");
            profileRepository.save(profile);

            Education edu1 = new Education();
            edu1.setSchool("某某大学");
            edu1.setDegree("学士");
            edu1.setMajor("计算机科学与技术");
            edu1.setStartDate("2013-09");
            edu1.setEndDate("2017-06");
            edu1.setDescription("主修数据结构、操作系统、计算机网络、软件工程、数据库系统、编译原理等课程，多次参与校级编程竞赛并获奖。");
            edu1.setOrderIndex(1);
            educationRepository.save(edu1);

            Education edu2 = new Education();
            edu2.setSchool("某某大学 · 继续教育学院");
            edu2.setDegree("在职研究生");
            edu2.setMajor("软件工程");
            edu2.setStartDate("2020-09");
            edu2.setEndDate("2022-06");
            edu2.setDescription("系统学习分布式系统设计、微服务架构、DevOps 与持续交付相关课程，并完成《基于 Spring Cloud 的电商订单系统重构》毕业课题。");
            edu2.setOrderIndex(2);
            educationRepository.save(edu2);

            Experience exp1 = new Experience();
            exp1.setCompany("某金融科技公司");
            exp1.setPosition("Java 开发工程师");
            exp1.setLocation("上海");
            exp1.setStartDate("2017-07");
            exp1.setEndDate("2019-02");
            exp1.setDescription("参与支付网关与清结算系统的开发与维护，负责核心交易流程的功能实现与性能优化，推动部分存量服务从单体应用拆分为微服务。");
            experienceRepository.save(exp1);

            Experience exp2 = new Experience();
            exp2.setCompany("某互联网公司");
            exp2.setPosition("高级后端工程师");
            exp2.setLocation("上海");
            exp2.setStartDate("2019-03");
            exp2.setEndDate("2022-12");
            exp2.setDescription("负责订单与库存域服务的设计与实现，引入分布式锁、消息最终一致性方案，保障大促场景下订单成功率与库存准确率。带领小组完成服务容器化与 CI/CD 落地，将发布效率提升 3 倍。");
            experienceRepository.save(exp2);

            Experience exp3 = new Experience();
            exp3.setCompany("某云计算公司");
            exp3.setPosition("资深后端工程师 / 技术负责人");
            exp3.setLocation("上海");
            exp3.setStartDate("2023-01");
            exp3.setEndDate("至今");
            exp3.setDescription("作为技术负责人牵头建设日志采集与监控平台，打通链路追踪、指标与告警体系；并负责关键业务的性能压测与容量规划，帮助业务顺利支撑多次峰值流量。");
            experienceRepository.save(exp3);

            Project project1 = new Project();
            project1.setName("高并发订单系统");
            project1.setRole("核心开发");
            project1.setStartDate("2021-01");
            project1.setEndDate("2022-06");
            project1.setDescription("负责订单写入、支付回调与状态流转等核心链路的设计与落地，引入异步化、削峰填谷与读写分离方案，使系统在大促峰值下仍能稳定处理每秒数万笔订单。");
            project1.setGithubUrl("https://github.com/zhangsan/order-system");
            project1.setDemoUrl("");
            project1.setTechStack("Java,Spring Boot,Spring Cloud,MySQL,Redis,RabbitMQ,Docker,Kubernetes");
            projectRepository.save(project1);

            Project project2 = new Project();
            project2.setName("可观测性平台建设");
            project2.setRole("项目负责人");
            project2.setStartDate("2022-03");
            project2.setEndDate("2023-02");
            project2.setDescription("从零搭建统一日志、指标与链路追踪平台，基于 OpenTelemetry 规范接入多语言服务，统一告警配置与排障视图，将平均故障定位时间从 30 分钟缩短到 5 分钟以内。");
            project2.setGithubUrl("");
            project2.setDemoUrl("");
            project2.setTechStack("Java,Spring Boot,Prometheus,Tempo,Loki,OpenTelemetry,Helm,Kubernetes,Grafana");
            projectRepository.save(project2);

            Project project3 = new Project();
            project3.setName("前后端一体化工程模板");
            project3.setRole("全栈开发");
            project3.setStartDate("2023-05");
            project3.setEndDate("2024-01");
            project3.setDescription("沉淀公司内部前后端脚手架，集成统一登录、前后端接口规范、Mock 能力、代码规范与 CI 流水线，一键生成可直接部署的全栈项目模板，大幅提升新项目搭建效率。");
            project3.setGithubUrl("https://github.com/zhangsan/fullstack-starter");
            project3.setDemoUrl("");
            project3.setTechStack("Java,Spring Boot,Vite,Vue 3,TypeScript,Element Plus,MySQL,Redis,GitHub Actions,Docker");
            projectRepository.save(project3);

            Skill java = new Skill();
            java.setName("Java");
            java.setCategory("language");
            java.setLevel(92);
            java.setDescription("熟悉 Java 语言特性与集合、并发、JVM 调优等，拥有丰富的高并发服务开发经验。");
            skillRepository.save(java);

            Skill springBoot = new Skill();
            springBoot.setName("Spring Boot & Spring Cloud");
            springBoot.setCategory("framework");
            springBoot.setLevel(90);
            springBoot.setDescription("精通基于 Spring Boot / Spring Cloud 的微服务开发，熟悉配置中心、注册发现、熔断限流与链路追踪等组件。");
            skillRepository.save(springBoot);

            Skill mysql = new Skill();
            mysql.setName("MySQL");
            mysql.setCategory("database");
            mysql.setLevel(88);
            mysql.setDescription("熟悉索引优化、分库分表与事务隔离级别，具备设计高并发读写场景下数据库方案的经验。");
            skillRepository.save(mysql);

            Skill redis = new Skill();
            redis.setName("Redis");
            redis.setCategory("cache");
            redis.setLevel(88);
            redis.setDescription("熟悉常用数据结构、分布式锁、缓存穿透/击穿/雪崩等问题及防护策略。");
            skillRepository.save(redis);

            Skill docker = new Skill();
            docker.setName("Docker & Kubernetes");
            docker.setCategory("devops");
            docker.setLevel(86);
            docker.setDescription("能够为复杂服务设计高质量 Dockerfile 与 docker-compose / Helm 配置，具备在 K8s 上部署与运维应用的实践经验。");
            skillRepository.save(docker);

            Skill frontend = new Skill();
            frontend.setName("前端与工程化");
            frontend.setCategory("frontend");
            frontend.setLevel(80);
            frontend.setDescription("掌握 Vue 3 / TypeScript / Vite 等技术栈，能够独立完成中小型前端应用开发与前后端联调。");
            skillRepository.save(frontend);

            Skill ciCd = new Skill();
            ciCd.setName("CI/CD 与自动化");
            ciCd.setCategory("devops");
            ciCd.setLevel(85);
            ciCd.setDescription("熟悉 GitHub Actions / Jenkins 等流水线工具，可搭建从代码检查、自动化测试到灰度发布的一体化流程。");
            skillRepository.save(ciCd);

            log.info("Seed data inserted successfully.");
        };
    }
}

