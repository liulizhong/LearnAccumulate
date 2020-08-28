/* 一、手写HQL 第1题:
        表结构：uid,subject_id,score
        求：找出所有科目成绩都大于学科平均成绩的用户*/
    --(1)建表：create table if not exists score(uid string,subject string,score int) row format delimited fields terminated by '/t';
    --(2)插数：insert into score values("1", "a", 50),("1", "b", 60),("1", "c", 70),("2", "a", 70),
        --            ("2", "b", 60),("2", "c", 80),("3", "a", 20),("3", "b", 60),("3", "c", 70);
    --(3)答案：
        select uid from--里3层筛选每一学科都大于平均分的uid并去重
        (
        select uid,if(score>=avg_score,0,1) flag from--里2层得出uid+是否超过平均分字段(0/1)
        (select uid,score,avg(score) over(partition by subject) avg_score from score t1) t2--里1层原表增加平均分字段
        ) t3
        group by uid  having sum(flag)=0;

/* 二、手写HQL 第2题
        我们有如下的用户访问数据
        userId	visitDate	visitCount
            u01	2017/1/21	5
            u02	2017/1/23	6
            u03	2017/1/22	8
            u04	2017/1/20	3
            u01	2017/1/23	6
            u01	2017/2/21	8
            U02	2017/1/23	6
            U01	2017/2/22	4
        要求使用SQL统计出每个用户的累积访问次数，如下表所示：
            用户id	月份	小计	累积
            u01	2017-01	11	11
            u01	2017-02	12	23
            u02	2017-01	12	12
            u03	2017-01	8	8
            u04	2017-01	3	3*/
    --(1)建表：create table test01_visit(userId string, visitData string, visitCount int) partitioned by(x string);
    --(2)插数：insert into test01_visit values("u01", "2017/1/21", 5),("u02", "2017/1/23", 6),("u03", "2017/1/22", 8),
        --               ("u04", "2017/1/20", 3),("u01", "2017/1/23", 6),("u01", "2017/2/21", 8),("U02", "2017/1/23", 6),("U01", "2017/2/22", 4);
    --(3)答案：
        select
        uid,data_month,visitCount,
        sum(visitCount) over(partition by uid order by data_month rows between unbounded preceding and current row)
        from
        (select lower(userId) uid,date_format(regexp_replace(visitData,'/','-'),'yyyy-MM') data_month,sum(visitcount) visitcount
        from test01_visit group by lower(userId),date_format(regexp_replace(visitData,'/','-'),'yyyy-MM')) t1;

/* 三、手写HQL 第3题
        有50W个京东店铺，每个顾客访客访问任何一个店铺的任何一个商品时都会产生一条访问日志，访问日志存储的表名为Visit，访客的用户id为user_id，被访问的店铺名称为shop，请统计：
        1、每个店铺的UV（访客数）
        2、每个店铺访问次数top3的访客信息。输出店铺名称、访客id、访问次数*/
    --(1)建表：create table visit(user_id string,shop string) row format delimited fields terminated by '\t';
    --(2)插数：insert into visit values("u1", "a"),("u2", "b"),("u1", "b"),("u1", "a"),("u3", "c"),("u4", "b"),("u1", "a"),("u2", "c"),("u5", "b"),
        --              ("u4", "b"),("u6", "c"),("u2", "c"),("u1", "b"),("u2", "a"),("u2", "a"),("u3", "a"),("u5", "a"),("u5", "a"),("u5", "a");
    --(3)答案
        select shop,count(distinct user_id) from visit group by shop;
        select shop,user_id,user_count,rmp from
        (select shop,user_id,count(*) user_count,row_number() over(partition by shop order by count(*) desc) rmp from visit group by shop,user_id) t1
        where rmp <= 3 order by shop,rmp;

/* 四、手写HQL 第4题
        已知一个表STG.ORDER，有如下字段:Date，Order_id，User_id，amount。请给出sql进行统计:数据样例:2017-01-01,10029028,1000003251,33.57。
        1）给出 2017年每个月的订单数、用户数、总成交金额。
        2）给出2017年11月的新客数(指在11月才有第一笔订单)*/
    --(1)建表：create table order_tab(dt string,order_id string,user_id string,amount decimal(10,2)) row format delimited fields terminated by '\t';
    --(2)插数：insert into order_tab values
        --      ("2017-01-01", "10029028","1000003251",33.57),("2017-01-03", "10029029","1000003234",34.57),("2017-01-02", "10029030","1000003251",46.57),
        --      ("2017-01-01", "10029048","1000003261",67.57),("2017-01-01", "10029047","1000003221",33.57),("2017-01-04", "10029046","1000003251",33.57),
        --      ("2017-01-03", "10029045","1000003251",43.57),("2017-01-07", "10029044","1000003251",98.57),("2017-02-01", "10029050","1000003251",45.57),
        --      ("2017-02-01", "10029051","1000003251",64.57),("2017-02-01", "10029052","1000003221",82.57),("2017-02-01", "10029053","1000003221",78.57),
        --      ("2017-02-01", "10029054","1000003241",45.57),("2017-02-01", "10029055","1000003271",15.57),("2017-02-01", "10029056","1000003281",12.57),
        --      ("2017-02-01", "10029057","1000003251",99.57),("2017-02-01", "10029058","1000003261",23.57),("2017-02-01", "10029059","1000003251",33.57);
    --(3)答案：
        select date_format(dt,'yyyy-MM'),count(distinct user_id),count(distinct order_id),sum(amount) from order_tab group by date_format(dt,'yyyy-MM')
        select t1.mn,count(*) from
        (select date_format(min(dt),'yyyy-MM') mn,user_id from order_tab group by user_id having date_format(min(dt),'yyyy-MM') = "2017-02" ) t1
        group by t1.mn;

/* 五、手写HQL 第5题
        有一个5000万的用户文件(user_id，name，age)，一个2亿记录的用户看电影的记录文件(user_id，url)，统计各年龄段观看电影的次数*/
    --(1)建表：create table usertb(user_id string,age int) row format delimited fields terminated by '\t';
          --   create table urltb(user_id string,url string) row format delimited fields terminated by '\t';
    --(2)插数：insert into usertb values("u1", 10),("u2", 12),("u3", 19),("u4", 28),("u5", 32);
        --  insert into urltb values("u1", "aaaa"),("u1", "ssdfs"),("u3", "s"),("u1", "x"),("u4", "x"),("u1", "x"),("u4", "x"),("u4", "x"),("u1", "adf");
    select t1.point,count(t2.url) from
    (select user_id,
    case when age < 18 then '未成年' when age < 50 then '成年人'else '老人'end as point
    from usertb) t1
    left join urltb t2 on t1.user_id = t2.user_id group by t1.point

/* 六、手写HQL 第6题
        有日志如下，请写出代码求得所有用户和活跃用户的总数及平均年龄。（活跃用户指连续两天都有访问记录的用户）
            日期 用户 年龄
            11,test_1,23
            11,test_2,19
            11,test_3,39
            11,test_1,23
            11,test_3,39
            11,test_1,23
            12,test_2,19
            13,test_1,23*/
    --(1)建表：CREATE TABLE user_age (dt string,user_id string, age INT ) ROW format delimited FIELDS TERMINATED BY ',';
    --(2)插数：insert into user_age values("2019-02-11","test_1",23),("2019-02-11","test_2",19),("2019-02-11","test_3",39),("2019-02-11","test_1",23),
        --("2019-02-11","test_3",39),("2019-02-11","test_1",23),("2019-02-12","test_2",19),("2019-02-13","test_1",23),("2019-02-15","test_2",19),("2019-02-16","test_2",19);
    --(3)答案：
        select sum(user_total_count),sum(user_total_avg_age),sum(twice_count),sum(twice_count_avg_age) from
        (select 0 user_total_count,0 user_total_avg_age,count(*) twice_count,cast(sum(age)/count(*) as decimal(10,2)) twice_count_avg_age from(
        select user_id,min(age) age from(     --里4层得出去重后的user_id,age
        select user_id,min(age) age from(     --里3层确定出连续连续两天活跃的用户
        select user_id,age,date_sub(dt,rk) flag from(	--里2层等到user_id,age,date_sub(dt,rk)
        select dt,user_id,min(age) age,rank() over(partition by user_id order by dt) rk from user_age group by dt,user_id  --里1层，去重dt,user_id
        ) t1)t2 group by user_id,flag having count(*)>=2)t3 group by user_id)t4
        union all
        select count(*) user_total_count,cast((sum(age)/count(*)) as decimal(10,1)),0 twice_count,0 twice_count_avg_age
        from(select user_id,min(age) age from user_age group by user_id)t5)t6;

/* 七、手写HQL 第7题
    请用sql写出所有用户中在今年10月份第一次购买商品的金额，表ordertable字段（购买用户：userid，金额：money，购买时间：paymenttime(格式：2017-10-01)，订单id：orderid）*/
    --(3)答案：
        SELECT userid,paymenttime,money,orderid from
        (SELECT userid,money,paymenttime,orderid,row_number() over (PARTITION BY userid ORDER BY paymenttime) rank
        FROM test_sql.test6 WHERE date_format(paymenttime,'yyyy-MM') = '2017-10') t WHERE rank = 1

/* 八、手写HQL 第8题
            有一个线上服务器访问日志格式如下（用sql答题）
            时间                      接口                              ip地址
            2016-11-09 11：22：05    /api/user/login                    110.23.5.33
            2016-11-09 11：23：10    /api/user/detail                   57.3.2.16
            .....
            2016-11-09 23：59：40    /api/user/login                    200.6.5.166
            求11月9号下午14点（14-15点），访问api/user/login接口的top10的ip地址*/
    --(3)答案：
        SELECT ip,count(*) AS cnt
        FROM test_sql.test8
        WHERE date_format(date,'yyyy-MM-dd HH') >= '2016-11-09 14'
          AND date_format(date,'yyyy-MM-dd HH') < '2016-11-09 15'
          AND interface='/api/user/login'
        GROUP BY ip ORDER BY cnt desc LIMIT 10;

/* 九、手写HQL 第9题
        有一个账号表如下，请写出SQL语句，查询各自区组的money排名前十的账号（分组取前10）
        CREATE TABIE `account`(
            `dist_id` int（11） DEFAULT NULL COMMENT '区组id'，
            `account` varchar（100）DEFAULT NULL COMMENT '账号' ,
            `gold` int（11）DEFAULT NULL COMMENT '金币'
            PRIMARY KEY （`dist_id`，`account_id`），
        ）ENGINE=InnoDB DEFAULT CHARSET-utf8     */
    --(3)答案:
        SELECT dist_id,
               account,
               gold
        FROM
             (SELECT dist_id,
                     account,
                     gold,
                     row_number () over (PARTITION BY dist_id
                         ORDER BY gold DESC) rank
              FROM test_sql.test10) t
        WHERE rank <= 10

/* 十、手写HQL 第10题
        1、有三张表分别为会员表（member）销售表（sale）退货表（regoods）
        （1）会员表有字段memberid（会员id，主键）credits（积分）；
        （2）销售表有字段memberid（会员id，外键）购买金额（MNAccount）；
        （3）退货表中有字段memberid（会员id，外键）退货金额（RMNAccount）；
        2、业务说明：
        （1）销售表中的销售记录可以是会员购买，也可是非会员购买。（即销售表中的memberid可以为空）
        （2）销售表中的一个会员可以有多条购买记录
        （3）退货表中的退货记录可以是会员，也可是非会员。一个会员可以有一条或多条退货记录
        查询需求：分组查出销售表中所有会员购买金额，同时分组查出退货表中所有会员的退货金额，把会员id相同的购买金额-退款金额得到的结果更新到表会员表中对应会员的积分字段（credits）*/
    --(3)答案：
        select t1.memberid,t2.MNAccount-t3.RMNAccount as credits from member t1
        left join
        (select memberid,sum(MNAccount) as MNAccount from sale where memberid is not null group by memberid) t2 on t1.memberid = t2.memberid
        left join
        (select memberid,sum(RMNAccount) as RMNAccount from regoods where memberid is not null group by memberid) t3 on t1.memberid = t3.memberid
/* 十一、手写HQL 第11题
                现在有三个表student（学生表）、course(课程表)、score（成绩单），结构如下：
                create table student
                (
                  id bigint comment ‘学号’，
                  name string comment ‘姓名’,
                  age bigint comment ‘年龄’
                );
                create table course
                (
                  cid string comment ‘课程号，001/002格式’,
                  cname string comment ‘课程名’
                );
                Create table score
                (
                  Id bigint comment ‘学号’,
                  cid string comment ‘课程号’,
                  score bigint comment ‘成绩’
                ) partitioned by(event_day string)

                其中score中的id、cid，分别是student、course中对应的列请根据上面的表结构，回答下面的问题
                1）请将本地文件（/home/users/test/20190301.csv）文件，加载到分区表score的20190301分区中，并覆盖之前的数据
                2）查出平均成绩大于60分的学生的姓名、年龄、平均成绩
                3）查出没有‘001’课程成绩的学生的姓名、年龄
                4）查出有‘001’\’002’这两门课程下，成绩排名前3的学生的姓名、年龄
                5）创建新的表score_20190317，并存入score表中20190317分区的数据
                6）描述一下union和union all的区别，以及在mysql和HQL中用法的不同之处？
                7）简单描述一下lateral view语法在HQL中的应用场景，并写一个HQL实例*/
    --(3)答案：
        1)load data local inpath '/home/users/test/20190301.csv' overwrite table score partition(event_day="20190301")
        2)select t1.name,t1.age,t2.avg_score
          from student t1
           left join
           (select id,avg(score) avg_score from score group by id)t2
           on t1.id = t2.id where t2.avg_score >= 60
        3)select t1.id,t1.name,t1.age from student9 t1
           left join
           (select id,sum(if(cid="001",1,0)) sum_score from score9 group by id) t2
           on t1.id = t2.id where t2.sum_score = 0;
        4)select cid,id,rnm from (select cid,id,row_number() over(partition by cid order by score desc) rnm from score9) t1 where rnm <= 3;
        5)create table score_20190317 as select * from score9 where event_day = "20190317";
        6) union的结果会自动进行“去重”且“排序”，union all则是直接将两个结果集合并，不会去重和排序。