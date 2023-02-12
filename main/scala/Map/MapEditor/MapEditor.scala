package Map.MapEditor

import Map.MapField

class MapEditor(mapFiled: MapField) {
  var mapField: MapField = mapFiled
  private var operationList: List[MapEditorOperation] = List[MapEditorOperation](InversionOperation, ReplacementOperation)


  private def writeMapEditorMeni(): Unit = {
    println()
    println("[1] Remove basic block")
    println("[2] Add basic block on boundary")
    println("[3] Change basic block to special")
    println("[4] Change special block to basic")
    println("[5] Set start block")
    println("[6] Set end block")
    println("[7] Filter blocks")
  }

  private def writeBasicAndCompositeOperations(): Unit = {
    println()
    println("Basic/Composite operations:")
    for(operation<- operationList){
      println(s"[${operation.getOperationName.toLowerCase}] ${operation.getOperationName} operation")
    }
    println(s"[add] Create new Composite operation")
  }

  private def writeMenus(): Unit = {
    writeMapEditorMeni()
    writeBasicAndCompositeOperations()
    println()
    println("[exit] Exit editor")
    println
    mapFiled.printMap()
  }

  def startEditor: MapField = {
    while(true) {
      try {
        writeMenus()
        scala.io.StdIn.readLine() match {
          case "1" => doOperation(removeBlockFromBoundary)
          case "2" => doOperation(setBlockOnBoundary)
          case "3" => doOperation(changeBasicBlockToSpecial)
          case "4" => doOperation(changeSpecialToBasicBlock)
          case "5" => doOperation(setStartingBlock)
          case "6" => doOperation(setEndBlock)
          case "7" =>
            //Filter function
            println("Read filer distance N:")
            val filterLength = scala.io.StdIn.readInt()
            doFilter(filterLength)

          case "add" =>
            addNewCommand()

          case "exit" =>
            return mapField
          case default =>
            operationList.find(operation => operation.getOperationName.toLowerCase() == default) match {
              case Some(operation) => operation.action(mapFiled)
              case None => println("No operation is found with this name")
            }
        }
      }
      catch {
        case ex:Exception =>print(s"${Console.RED}An exception occurred while editing map: $ex ${Console.RESET}")
      }
    }
    mapField
  } :MapField

  private def removeBlockFromBoundary(row: Int, col: Int): Unit = {
    val symbol = mapFiled.map(row)(col)
    if(symbol != 'o') throw new Exception("Can't remove a non-basic block")

    mapField.setSymbolOnPosition('-', row, col)
  }

  private def setBlockOnBoundary(row: Int, col: Int): Unit = {
    val symbol = mapFiled.map(row)(col)
    if (symbol != '-') throw new Exception("Can't remove a non-basic block")

    mapField.setSymbolOnPosition('o', row, col)
  }

  private def changeSpecialToBasicBlock(row: Int, col: Int): Unit = {
    val symbol = mapFiled.map(row)(col)
    if (symbol != '.') throw new Exception("Can't change a non-special block to basic")

    mapField.setSymbolOnPosition('o', row, col)
  }

  private def changeBasicBlockToSpecial(row: Int, col: Int): Unit = {
    val symbol = mapFiled.map(row)(col)
    if (symbol != 'o') throw new Exception("Can't change a non-basic block to special")

    mapField.setSymbolOnPosition('.', row, col)
  }

  private def setStartingBlock(row: Int, col: Int): Unit = {
    val startingPos = mapFiled.getStartPosition
    val oldSymbol = mapFiled.map(row)(col)

    if(oldSymbol == 'S' || oldSymbol == 'T')  return
    mapField.setSymbolOnPosition('S', row, col)
    mapField.setSymbolOnPosition('o', startingPos.row, startingPos.col)
  }

  private def setEndBlock(row: Int, col: Int): Unit = {
    val endPos = mapFiled.getEndPosition
    val oldSymbol = mapFiled.map(row)(col)

    if(oldSymbol == 'S' || oldSymbol == 'T') return
    mapField.setSymbolOnPosition('T', row, col)
    mapField.setSymbolOnPosition('o', endPos.row, endPos.col)
  }

  private def doOperation(action: (Int,Int) => Unit): Unit = {
    println("Read row:")
    val row = scala.io.StdIn.readInt()

    println("Read col:")
    val col = scala.io.StdIn.readInt()

    action(row,col)
  }

  private def addNewCommand(): Unit = {
    println("Name of new command")
    val operationName = scala.io.StdIn.readLine()
    var actionList = List[MapField => Unit]()
    var operationSequence = "|"
    while (true) {
      println()
      println(s"Current sequence for new command: $operationSequence")
      println("Add operation to sequence:")
      for (operation <- operationList) {
        println(s"[${operation.getOperationName.toLowerCase}] ${operation.getOperationName} operation")
      }
      println("\n[exit] Exit")

      scala.io.StdIn.readLine() match {
        case "exit" =>
          operationList = operationList.appended(
            new CompositeMapEditorOperation(operationName, actionList))

          return
        case default =>

          operationList.find(operation => operation.getOperationName.toLowerCase() == default) match {
            case Some(operation) =>
              operationSequence = operationSequence.concat(operation.getOperationName)+ "|"
              actionList= actionList.appended(operation.action)
            case None => println("No operation is found with this name")
          }
      }
    }
  }

  private def doFilter(filterLength: Int): Unit = {
    val mapCopy = mapFiled.makeMapCopy


    for (i <- 0 until mapFiled.n) {
      for (j <- 0 until mapFiled.m) if (mapCopy(i)(j) == '.') {

        var index = 1
        var isEnd = false

        while (index <= filterLength && i + index < mapFiled.n && !isEnd) {
          if(mapCopy(i + index)(j) == '.') isEnd = true
          index +=1
        }

        index = 1
        while (index <= filterLength && i -index >=0 && !isEnd) {
          if (mapCopy(i -index)(j) == '.') isEnd = true
          index += 1
        }

        while (index <= filterLength && j + index < mapFiled.m && !isEnd) {
          if (mapCopy(i)(j + index) == '.') isEnd = true
          index += 1
        }

        index = 1
        while (index <= filterLength && j - index >= 0 && !isEnd) {
          if (mapCopy(i)(j - index) == '.') isEnd = true
          index += 1
        }

        if(isEnd) mapFiled.map(i)(j) = 'o'
      }
    }
  }
}
