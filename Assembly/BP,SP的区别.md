#BP,SP的区别
SP是用在栈上的，配合SS使用，像SS:SP

SS上放段地址，SP上放偏移地址。

寻址时，像[bp]，相当于SS：[bp]

就是说它默认使用SS

像BX默认使用CS

---------------------------------------------------
ss栈段寄存器

sp栈顶指针寄存器

bp默认的栈基址寄存器

---------------------------------------------------
sp会随着带有堆栈操作的指令(比如PUSH、CALL、INT、RETF)产生变化，
而BP不会，所以在带参数的子过程中用BP来获取参数和访问设在堆栈里面的临时变量。


