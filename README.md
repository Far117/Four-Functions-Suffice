# Four Functions Suffice
Given some function `f(x)`, this program will generate functions which approximate it from only addition, subtraction, multiplication, and division. The approximations use genetic algorithms in order to improve.

### Examples

With an input of `f(x) = sin(x)`, we can receive an output of:

<p align="center">
  <img width="1040" height="114" src="https://cdn.discordapp.com/attachments/153337086466981888/482830525258792961/unknown.png">
</p>

This function has an average error of less than 0.01 across the domain of `sin` (`0` to `2pi`)

### Grammar

The general idea is based on a grammar of arithmetic expressions. An expression takes the form of any of the following:

- \<Number\>
- \<Variable\>
- \<Unary operator\> \<Expression\>
- \<Expression\> \<Binary operator\> \<Expression\>

Note that this is a recursive definition, so these structures can be arbitrarily deep.

For example, here's `2 + 2`, represented as a `Number BinaryOperator Number`:

<p align="center">
  <img width="418" height="366" src="http://i.imgur.com/gSNfGi4.png">
</p>

The biggest benefit of this structure is that any given node can be removed and replaced with another `Expression`, and so long as that `Expression` tree is also grammatically-correct, then the entire tree will be grammatically-correct as well.

### Improving Approximations
For this program, "grammatically-correct" is synonymous with "computable" (barring division-by-zero errors), so we can push inputs through these functions to test how good they are at approximating the target `f(x)`. 

The best function of a generation is then copied many times, where each copy has a random mutation at one of its nodes. These mutations can either modify a node, or replace it with a new tree entirely. Over time, there is a convergence on a function which approximates `f(x)`.