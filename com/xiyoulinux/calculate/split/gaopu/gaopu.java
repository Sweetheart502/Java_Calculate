//	生成新的一个List返回逆波兰表达式
	public List<String> toSuffixExpression(List<String> normalExpression) {
        //首先是处理数学函数内部的东西,例如log(2,long(2,32))---->log(2,5)


        //========================================================================================================
        //下面是转后缀表达式
        getPriority("/home/geekgao/priority");//获取运算符优先级
        Set<String> operator = operatorPriority.keySet();//获得运算符集合
        List<String> suffixExpression = new LinkedList<String>();//存储最终的后缀表达式
        Stack<String> temp = new Stack<String>();//转换后缀表达式需要的栈

        //从前往后扫描表达式
        for (int i = 0;i < normalExpression.size();i++) {
            if (normalExpression.get(i).equals("(")) {//如果是左括号那么入栈
                temp.push(normalExpression.get(i));
            } else if (normalExpression.get(i).equals(")")) {//如果是右括号
                while (!temp.empty() && (!temp.peek().equals("("))) {
                    suffixExpression.add(temp.pop());
                }
                temp.pop();//弹出左括号
            } else if (!operator.contains(normalExpression.get(i))) {//不在运算符集合中包括,这下肯定是数字了
                suffixExpression.add(normalExpression.get(i));
            }

            //不满足上述情况那么就是运算符了,下面处理
            else if (temp.isEmpty() || temp.peek().equals("(")) {//如果是空栈或者是左括号,直接入栈
                temp.push(normalExpression.get(i));
            } else if (operatorPriority.get(normalExpression.get(i)) <= operatorPriority.get(temp.peek())) {//当前运算符优先级>=栈顶运算符优先级,直接入栈当前运算符
                temp.push(normalExpression.get(i));
            } else {//当前运算符优先级<栈顶运算符优先级,一个个出栈
                while (!temp.isEmpty() && operatorPriority.get(normalExpression.get(i)) > operatorPriority.get(temp.peek())) {
                    suffixExpression.add(temp.pop());
                }
                temp.push(normalExpression.get(i));
            }

        }

        while (!temp.isEmpty()) {
            suffixExpression.add(temp.pop());
        }

        return suffixExpression;
    }
