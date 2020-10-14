--#-#  再次复习：177、180、182、197、534、550、569、571
175. 组合两个表:
        select
        t1.FirstName, t1.LastName, t2.City, t2.State
        from Person t1
        left join
        Address t2
        on t1.PersonId = t2.PersonId

176. 第二高的薪水
        select
        ifnull(
          (select distinct salary
           from employee
           order by salary desc
            limit 1 offset 1),
        null) as secondhighestsalary

177. 第 N 高的薪水
        SELECT IFNULL(
        (select salary
        from(
        select salary,
        rank() over(order by salary desc) rk
        from Employee
            group by salary
        )t1
        where rk=N+1),NULL) SecondHighestSalary

178. 分数排名
      select Score,dense_rank() over(order by Score desc) `Rank` from Scores

180. 连续出现的数字
      select distinct(Num) ConsecutiveNums from (
      select Num,
      lead(Num,1,null) over() `n2`,
      lead(Num,2,null) over() `n3`
      from Logs) t1 where Num=n2 and Num=n3

181. 超过经理收入的员工
      select t1.Name Employee
      from Employee t1
      left join Employee t2
      on t1.ManagerId = t2.Id
      where t1.Salary > t2.Salary

182. 查找重复的电子邮箱
      select Email from Person group by Email having count(*) > 1

183. 从不订购的客户
      select Name Customers from Customers
      where Id not in (select CustomerId from Orders)

184. 部门工资最高的员工
      select t2.Name Department,t1.Name Employee,t1.Salary
      from
      (
      select Name,Salary,DepartmentId,rank() over(partition by DepartmentId order by Salary desc) `rank` from Employee
      ) t1
      left join Department t2
      on t1.DepartmentId = t2.Id
      where t1.rank = 1 and t2.Name is not null

185. 部门工资前三高的所有员工
      select
      t2.Name Department,t1.Name Employee,t1.Salary
      from
      (select Name,Salary,DepartmentId,dense_rank() over(partition by DepartmentId order by Salary desc) `rank` from Employee) t1
      left join Department t2
      on t1.DepartmentId = t2.Id
      where t1.rank <= 3 and t2.Id is not null

196. 删除重复的电子邮箱
      hive> insert overwrite Person select min(Id),Email from Person group by Email
      mysql> delete p1 from Person p1,Person p2 where p1.Email = p2.Email and p1.id > p2.Id

197. 上升的温度
      select a.Id from Weather a join Weather b on dateDiff(a.RecordDate,b.RecordDate) = 1 and a.Temperature > b.Temperature

262. 行程和用户
      select
      Request_at Day,
      round(sum(if(Status = 'completed',0,1))/count(Status),2) `Cancellation Rate`
      from Trips
      where
      Client_Id not in (select Users_Id from Users where Banned = 'Yes')
      and Driver_Id not in (select Users_Id from Users where Banned = 'Yes')
      and Request_at between '2013-10-01' and '2013-10-03'
      group by Request_at

511. 游戏玩法分析 I
      select player_id,min(event_date) first_login from Activity group by player_id

512. 游戏玩法分析 II
      select player_id,device_id from
      (select player_id,device_id,rank() over(partition by player_id order by event_date) `rnb` from  Activity) tem
      where rnb = 1

534. 游戏玩法分析 III
      方法一：开窗
        select player_id,event_date,
        sum(games_played) over(partition byplayer_id order by event_date rows between UNBOUNDED PRECEDING and CURRENT ROW) games_played_so_far
        from Activity
      方法二：自连接：
        select
            a1.player_id,
            a1.event_date,
            sum(a2.games_played) games_played_so_far
        from Activity a1,Activity a2
        where a1.player_id=a2.player_id and
              a1.event_date>=a2.event_date
        group by a1.player_id,a1.event_date;

550. 游戏玩法分析 IV
      select round(avg(a.event_date is not null), 2) `fraction` from
      (select player_id,min(event_date) `login` from Activity group by player_id) t1
      left join Activity t2 on t1.player_id = t2.player_id and datediff(t1.login,t2.event_date) = 1

569. 员工薪水中位数
      select Id,Company,Salary from
          (select Id,Company,Salary,
          row_number() over(partition by Company order by Salary) `rnb`
          sum(1) over(partition by Company) `sum`
          from Employee)
      where rnb >= round((sum/2,2)) rnb <= round((sum+2)/2,2)

570. 至少有5名直接下属的经理
      select Name from Employee where Id in (select ManagerId from Employee group by ManagerId having count(*) >= 5)

571. 给定数字的频率查询中位数
      select
          avg(cast(number as float)) median
      from
          (
              select
                  Number,
                  Frequency,
                  sum(Frequency) over(order by Number) - Frequency prev_sum,
                  sum(Frequency) over(order by Number) curr_sum
              from Numbers
          ) t1,
          (
              select
                  sum(Frequency) total_sum
              from Numbers
          ) t2
      where
          t1.prev_sum <= (cast(t2.total_sum as float) / 2) and
          t1.curr_sum >= (cast(t2.total_sum as float) / 2)

574. 当选者
      select Name from Candidate where id in
        (select CandidateId from (select CandidateId,rank() over(partition by CandidateId) `rnk` from Vote) t1 where rnk = 1)

577. 员工奖金
      select t1.name,t2.bonus
      from Employee t1  left join Bonus t2 on t1.empId = t2.empId
      where t2.bonus < 1000 or t2.empId is null

578. 查询回答率最高的问题
      select question_id from survey_log group by question_id order by round(sum(if(action = 'answer',1,0)),2)/round(sum(if(action = 'show',1,0)),2) desc limit 1

579. 查询员工的累计薪水
      select Id,Month,
      sum(Salary) over(partition by Id order by Month ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) Salary
      from
      (
          select Id,Month,Salary,
          lead(Month,1,0) over(partition by Id order by Month) lm
          from Employee
      )t1
      where lm != 0
      order by  Id,Month desc

580. 统计各专业学生人数
      select  dept_name ,count(student_id) student_number
      from department d left join student s
      on d.dept_id=s.dept_id
      group by dept_name
      order by student_number desc

584. 寻找用户推荐人
      SELECT name FROM customer WHERE referee_id != 2 OR referee_id IS NULL;

585. 2016年的投资