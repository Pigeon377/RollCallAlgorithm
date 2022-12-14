# RollCallAlgorithm

福州带🧱20届软工实践第二次结对作业 (🐋🐉)

## 结论

实现请见 `kotlin`: __org.alg.RollCallSchema__

| 算法                                   | 正确率         |
|--------------------------------------|-------------|
| onlyRollCallFrequentlyAbsentStudents | 0.80 ± 0.03 | 
| rollCallStudentsPartlyBasedOnGpa     | 0.12 ± 0.02 | 
| knn                                  | 0.81 ± 0.01 | 

图像

![res.jpg](draw/res.jpg)

## 环境搭建

在开始之前，请确保你的电脑上已经安装了

- golang (version >= 1.18)
- kotlin (version >= 1.6.20)

不了解如何安装的`windows`用户，可以从官方网站下载`msi`的安装包，可以做到一键安装，不需要配置环境。

[kotlin release](https://github.com/JetBrains/kotlin/releases/tag/v1.7.20)  
[golang download](https://go.dev/dl/)

对于`linux`用户,使用包管理器安装即可

## 运行代码

在`git clone`该项目后，进入 __src/main/go__


> ps: 如果下列操作难以完成，请使用仓库中 __/executable__ 文件夹下的两个可执行文件，分别对应数据生成器和算法实现。
> 其中:
>
> __gen.exe__  =>  数据生成器  
> ![](media/gen.exe.gif)
>
> __rollcall.exe__  =>  算法实现
> ![](media/rollcall.exe.gif)
> __gen.exe__ 需要先执行，请注意顺序  
> __gen.exe__ 通过`go build` 命令生成  
> __rollcall.exe__ 通过`kotlin-native`编译器生成，对应代码在 __src/main/kotlin-native/__
> 文件夹下

### 数据生成

运行`go mod tidy`，及`go run main.go`
会在 __data__ 文件夹下生成五门课程的随机数据

单个文件共21行
第1行为`gpa`，第2至21行为每门课的缺勤情况，
缺勤记为`1`,到勤记为`0`

### 结果测试

- 使用`gradle build`构建项目，等待构建完成后，在 __src/main/kotlin/Main.kt__ 中执行主函数即可
- 亦可直接直接使用`kotlinc`直接编译，后续使用和`java`一致

结果可以输出到控制台或是文件

通过在`Main.kt`使用不同的`Onput`接口实现来改变输出方式

### 结果

生成的随机数据存放在 __项目根目录/data__ 目录下，采用*csv*格式保存
算法的结果存放在 __项目根目录/res__ 目录下，采用*json*格式保存

## 算法

$$ E = \frac{五门课程的有效点名次数}{总请求次数} $$

__要求能够最小化向后端发送的请求次数，最大化抓出缺勤同学的数量__

即求解 *E* 的最大值

当仅请求一次时, 若为有效点名i

$$ E = 1 $$

为最优解
若为无效点名

$$ E = 0 $$

稳定性较差

由于随机数的生成符合均匀分布，且每节课中的0-3名翘课的同学是无法预测的。
随机抽点有很强的现实意义，但是对于此题中的*E*，随机抽点必然降低*E*值及抽点方案的稳定性。
因此，最合理的策略应当是:
__找出5-8名有80%缺课率的学生，然后在二十节课中每次都抽点这些学生，能保证结果具有稳定性，且*E*值在0.8上下浮动__

~~数学语言不会写也不会证明 差不多得了~~

## 以上

----------------------------------

## 扩展

### 输入

实现`Input`接口，保证返回值正确

### 输出

实现`Onput`接口，内部需要定义一个`mutable`的`collection`以存储数据，
`write`不触发实际的写出，将数据全部写完后调用`output`产生输出

~~不用`abstract class`是因为蓝色没有绿色好看~~

### 算法

定义`lambda`表达式，类型为`RollCallSchema`,
即`(List<Lesson>, List<Double>, Int) -> List<Lesson>`类型

类型修饰符请置为`internal`

### 其他

遍历数据文件和计算*E*值等方法已给出

调用上述的模块的逻辑位于*Main.kt*
