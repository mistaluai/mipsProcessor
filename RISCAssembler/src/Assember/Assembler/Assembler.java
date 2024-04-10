package Assember.Assembler;


import Assember.Decoders.*;
import Assember.Utils.BinaryOperations;
import Assember.Utils.SymbolTable;

public class Assembler {
    private SymbolTable st;
    private InstructionsOperations instructionsOperations;

    private JTypeDecoder jtd;
    private RTypeDecoder rtd;
    private ITypeDecoder itd;
    private LoadStoreDecoder lstd;
    private BTypeDecoder btd;

    private String assemblyCode;
    private StringBuilder binaryCode;
    private StringBuilder instructionCode;

    int currentInstruction;
    public Assembler(String code) {
        st = new SymbolTable(code);
        instructionsOperations = new InstructionsOperations();

        rtd = new RTypeDecoder();
        itd = new ITypeDecoder();
        lstd = new LoadStoreDecoder();
        jtd = new JTypeDecoder(st);
        btd = new BTypeDecoder(st);

        assemblyCode = code;
        binaryCode = new StringBuilder();
        instructionCode = new StringBuilder();
        currentInstruction = 0;
    }
    /**
     * Assembles the assembly code by iterating over each instruction, decoding it,
     * and appending its corresponding binary representation to the binary code StringBuilder.
     * Skippable instructions, such as those containing labels, are ignored during assembly.
     */
    public void assemble() {
        // Split the assembly code into lines
        String[] lines = assemblyCode.split("\n");

        // Iterate over each instruction in the assembly code
        for (String instruction : lines) {
            // Trim the instruction to remove leading and trailing whitespaces
            instruction = instruction.trim();

            // Skip empty lines or comments
            if (instruction.length() == 0 || instruction.charAt(0) == '#')
                continue;

            instruction = removeComments(instruction);

            // Skip skippable instructions (those containing labels)
            if (st.isSkippable(instruction))
                continue;

            System.out.println(currentInstruction + " " + instruction);

            // Decode the instruction based on its type and append the binary representation
            // to the binary code StringBuilder
            String machineCode = "";
            switch (instructionsOperations.getInstructionType(instruction)) {
                case RType:
                   machineCode = rtd.decodeInstruction(instruction, currentInstruction);
                    break;
                case IType:
                    machineCode = itd.decodeInstruction(instruction, currentInstruction);
                    break;
                case JType:
                    machineCode = jtd.decodeInstruction(instruction, currentInstruction);
                    break;
                case LoadStoreType:
                    machineCode = lstd.decodeInstruction(instruction, currentInstruction);
                    break;
                case BType:
                    machineCode = btd.decodeInstruction(instruction, currentInstruction);
                    break;
            }

            // Append a newline character after each instruction
            binaryCode.append(machineCode + "\n");
            instructionCode.append(instruction + " : " + machineCode +" | " + BinaryOperations.binaryToHex(machineCode) + "\n");
            // Increment the current instruction counter
            currentInstruction++;
        }
    }

        private String removeComments(String assemblyCode) {
            StringBuilder result = new StringBuilder();
            boolean inComment = false;

            for (int i = 0; i < assemblyCode.length(); i++) {
                char currentChar = assemblyCode.charAt(i);
                char nextChar = i < assemblyCode.length() - 1 ? assemblyCode.charAt(i + 1) : '\0';

                if (currentChar == '#' && (i == 0 || assemblyCode.charAt(i - 1) != '\\')) {
                    inComment = true;
                }

                if (!inComment) {
                    result.append(currentChar);
                }

                if (currentChar == '\n' || (currentChar == '\r' && nextChar == '\n')) {
                    inComment = false;
                }
            }
            String output = result.toString();
            output = output.trim();
            return output;
        }

    public String[][] getSymbols() {
        return st.getSymbols();
    }

    public String getBinaryCode() {
        return binaryCode.toString();
    }
    public String getDebugCode() {
        return instructionCode.toString();
    }
    public int getCurrentInstruction()
    {
        return currentInstruction;
    }
}
