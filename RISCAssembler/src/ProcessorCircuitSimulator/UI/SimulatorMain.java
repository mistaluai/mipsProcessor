package ProcessorCircuitSimulator.UI;

import Assember.Assembler.Assembler;
import ProcessorCircuitSimulator.Processor.Processor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorMain extends JFrame {

    private DefaultTableModel registerModel, memoryModel, instructionModel;
    private Processor processor;
    private Assembler assembler;

    public SimulatorMain(Assembler assembler, Processor processor) {
        this.assembler = assembler;
        this.processor = processor;
        processor.loadProgram();


        setTitle("RISC Processor Simulator");
        setSize(800, 600);

        initializeMenuBar();

        // Create JTable for the instructions
        String[] instructionColumns = {"Index", "Compiled", "Instruction"};
        instructionModel = new DefaultTableModel(assembler.getInstructions(), instructionColumns);
        JTable instructionTable = new JTable(instructionModel);
        JScrollPane instructionScrollPane = new JScrollPane(instructionTable);
        instructionScrollPane.setPreferredSize(new Dimension((int) (getWidth() * 0.75), getHeight()));
        add(instructionScrollPane, BorderLayout.CENTER);

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        // Create JTable for the registers and values
        String[] registerColumns = {"Registers", "Values"};
        registerModel = new DefaultTableModel(null, registerColumns);
        JTable registerTable = new JTable(registerModel);
        JScrollPane registerScrollPane = new JScrollPane(registerTable);
        registerScrollPane.setPreferredSize(new Dimension((int) (getWidth() * 0.25), (int) (getHeight() * 0.25)));
        dataPanel.add(registerScrollPane);

        // Create JTable for the registers and values
        String[] symbolTableColumns = {"Label", "Address"};
        DefaultTableModel symbolTableModel = new DefaultTableModel(assembler.getSymbols(), symbolTableColumns);
        JTable symbolTable = new JTable(symbolTableModel);
        JScrollPane symbolTableScrollPane = new JScrollPane(symbolTable);
        symbolTableScrollPane.setPreferredSize(new Dimension((int) (getWidth() * 0.25), (int) (getHeight() * 0.25)));
        dataPanel.add(symbolTableScrollPane);

        // Create JTable for the memory addresses and values
        String[] memoryColumns = {"Address", "Value"};
        memoryModel = new DefaultTableModel(null, memoryColumns);
        JTable memoryTable = new JTable(memoryModel);
        JScrollPane memoryScrollPane = new JScrollPane(memoryTable);
        memoryScrollPane.setPreferredSize(new Dimension((int) (getWidth() * 0.25), (int) (getHeight() * 0.75)));
        dataPanel.add(memoryScrollPane);

        add(dataPanel, BorderLayout.EAST);


        updateRegisters();
        updateMemory();

        setVisible(true);
    }

    private void initializeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Simulate menu
        JMenu simulateMenu = new JMenu("Simulate");
        JMenuItem runNextInstructionItem = new JMenuItem("Run Next Instruction");
        JMenuItem runProgramItem = new JMenuItem("Run Program");
        simulateMenu.add(runNextInstructionItem);
        simulateMenu.add(runProgramItem);

        // Add menus to menu bar
        menuBar.add(simulateMenu);

        setJMenuBar(menuBar);

        // Add action listeners for menu items
        runNextInstructionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processor.performCycle();
                updateRegisters();
                updateMemory();
                updateCurrentInstruction(processor.getCurrentInstruction());
            }
        });

        runProgramItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processor.executeProgram();
                updateRegisters();
                updateMemory();
            }
        });


    }

    public void updateCurrentInstruction(int index) {
        if (index >= 0 && index < instructionModel.getRowCount()) {
            JTable instructionTable = (JTable) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView();
            instructionTable.setRowSelectionInterval(index, index);
            instructionTable.scrollRectToVisible(instructionTable.getCellRect(index, 0, true));
        } else {
            System.out.println("Invalid index");
        }
    }

    private void updateRegisters() {
        String[] registers = processor.getRegisters();
        // Simulated data update
        String[][] newData = {
                {"R1", registers[1],},
                {"R2", registers[2],},
                {"R3", registers[3],},
                {"R4", registers[4],},
                {"R5", registers[5],},
                {"R6", registers[6],},
                {"R7", registers[7]},
                {"PC", String.valueOf(processor.getCurrentInstruction())}
        };

        // Clear the existing data
        registerModel.setRowCount(0);

        // Add new data to the table
        for (String[] row : newData) {
            registerModel.addRow(row);
        }
    }

    private void updateMemory() {
        // Simulated data update
        String[][] newData = processor.getMemory();

        // Clear the existing data
        memoryModel.setRowCount(0);

        // Add new data to the table
        for (String[] row : newData) {
            memoryModel.addRow(row);
        }
    }
}
