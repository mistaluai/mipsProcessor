package ProcessorCircuitSimulator.Instructions.JumpInstructions;

import ProcessorCircuitSimulator.DataPath.DataMemory;
import ProcessorCircuitSimulator.DataPath.ProgramCounter;
import ProcessorCircuitSimulator.DataPath.RegisterFile;
import ProcessorCircuitSimulator.Instructions.Instruction;

import static Assember.Utils.BinaryOperations.hexString;

public class JumpLinkInstruction extends Instruction {
    public JumpLinkInstruction(RegisterFile registerFile, DataMemory memory, ProgramCounter programCounter, int[] parameters) {
        super(registerFile, memory, programCounter, parameters);
    }

    public void execute() {

        int returnValue = programCounter.getCurrentValue();
        String returnHex = hexString(returnValue, 0);
        registerFile.setRegister(7, returnHex);

        int offset = parameters[0];
        programCounter.offsetCounter(offset);
    }
}
