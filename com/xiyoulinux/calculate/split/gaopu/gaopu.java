//	生成新的一个List返回逆波兰表达式
    public List<String> toSuffixExpression(List<String> normalExpression) {
        //首先是化简函数
        simpleFunction(normalExpression);
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
    
    //处理数学函数内部的东西,例如log(2,log(2,30 + 2))---->log(2,5)
    private void simpleFunction(List<String> normalExpression) {
        Set<String> operator = operatorPriority.keySet();//得到运算符集合
        for (int i = 0;i < normalExpression.size();i++) {
            String currentStr = normalExpression.get(i);
            //=============================================================================
            //下面排除不用处理这个节点的情况

            //(1)这个节点是运算符那么就不用处理
            if (operator.contains(currentStr) || currentStr.equals("(") || currentStr.equals(")")) {
                continue;
            }

            //(2)这个节点没有函数不用处理
            String[] mathFunctionArray = (String[]) mathFunction.toArray();//获得数学函数数组,比较当前节点是否含有数学函数
            int j;
            //判断这个节点是否含有函数
            for (j = 0; j < mathFunctionArray.length && !currentStr.contains(mathFunctionArray[j]); j++)
                ;
            if (j == mathFunctionArray.length) {
                continue;
            }

            //(3)到这了说明含有函数,函数内部没有函数并且没有运算符也不用计算
            String functionInnerStr = currentStr.substring(currentStr.indexOf('('), currentStr.lastIndexOf(')'));
            String [] operatorArray = (String[]) operator.toArray();//获得运算符数组,比较函数内部是否含有运算符

            //判断函数内部是否含有函数
            for (j = 0; j < mathFunctionArray.length && !functionInnerStr.contains(mathFunctionArray[j]); j++)
                ;
            int k;
            //判断函数内部是否含有运算符
            for (k = 0; k < operatorArray.length && !functionInnerStr.contains(operatorArray[k]); k++)
                ;

            if (j == mathFunctionArray.length && k == operatorArray.length) {
                continue;
            }
            //=============================================================================
            //函数内部有需要计算的东西,需要把里面的计算出来替换函数内部的东西
            String [] functionParam = functionInnerStr.split(",");//有的函数有两个参数,按照逗号分开的,每一部分都处理
            //处理办法是给函数的参数后面加一个"+0",然后进行同样的流程获得最后的结果放进函数参数的相应位置
            for (int m = 0;m < functionParam.length;m++) {
                functionParam[m] = functionParam[m].concat("+0");
                String resultStr = calculate(toSuffixExpression(toNormalExpression(functionParam[m])));
                currentStr = currentStr.replace(functionParam[m],resultStr);
            }
            normalExpression.set(i,currentStr);
        }
    }
