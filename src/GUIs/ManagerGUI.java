package GUIs;

import Exceptions.SubdivisionException;
import main.Driver;
import main.Floor;
import main.Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ManagerGUI implements ItemListener
{
    private Manager man;
    private JPanel cards;  //Pannello che usa CardLayout
    final private static String MAKEFLOORS = "Crea piani";
    final private static String TARRIFF = "Scegli tariffa";
    final private static String REMOVEFLOOR = "Rimuovi piano";
    final private static String SUBDIVISION = "Scegli suddivisione posti";
    final private static String DRIVERINFO = "Visualizza informazioni clienti";


    public ManagerGUI(Manager man)
    {
        this.man = man;
        JFrame f = new JFrame();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        f.setSize(screenWidth / 2, screenHeight / 2);
        f.setLocation(screenWidth / 4, screenHeight / 4);
        f.setTitle("Manager");

        initComponents(f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private void initComponents(JFrame f)
    {
        //Metto JComboBox in un JPanel per un look migliore
        JPanel comboBoxPane = new JPanel();
        comboBoxPane.setLayout(new GridLayout(1,2));
        String comboBoxItems[] = {MAKEFLOORS, REMOVEFLOOR, TARRIFF, SUBDIVISION, DRIVERINFO};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        JTextField jf = new JTextField("Cosa vuoi fare?");
        jf.setEditable(false);
        comboBoxPane.add(jf);
        comboBoxPane.add(cb);
        comboBoxPane.setPreferredSize(new Dimension(500, 100));
        setFont(comboBoxPane, new Font("Helvetica", Font.PLAIN, 30));

        //Schermata 1: crea i piani
        JPanel card1 = makeFloorsCard();
        //Schermata 2: elimina piano
        JPanel card2 = removeFloorCard();
        //Schermata 3: tariffa
        JPanel card3 = chooseTariffCard();
        //Schermata 4: divisione posti standard/in abbonamento
        JPanel card4 = chooseSubdivisionCard();
        //Schermata 5: visualizza informazioni sui clienti nel parcheggio
        JPanel card5 = showDriversInfo();

        //Creo il pannello che contiene le "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, MAKEFLOORS);
        cards.add(card2, REMOVEFLOOR);
        cards.add(card3, TARRIFF);
        cards.add(card4, SUBDIVISION);
        cards.add(card5, DRIVERINFO);

        f.add(comboBoxPane, BorderLayout.NORTH);
        f.add(cards, BorderLayout.CENTER);
    }

    //***********Creo le schermate del manager*********************

    private JPanel makeFloorsCard()
    {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        JTextField jf1 = new JTextField("Numero piani: ");
        JTextField jf2 = new JTextField("Numero posti: ");
        jf1.setEditable(false);
        jf2.setEditable(false);
        JTextField floors = new JTextField();
        JTextField spaces = new JTextField();
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setLineWrap(true);
        //Reinizializzo JTextArea quando cambio card
        card.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                info.setText("");
            }
        });
        JScrollPane scroll = new JScrollPane(info);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        topPanel.add(jf1); topPanel.add(floors); topPanel.add(jf2); topPanel.add(spaces);
        topPanel.setPreferredSize(new Dimension(500,100));
        JButton create = new JButton("Crea");
        create.setPreferredSize(new Dimension(200,100));
        create.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    man.makeFloors(Integer.parseInt(floors.getText()), Integer.parseInt(spaces.getText()));
                    ArrayList<Floor> fl = man.getFloorsList();
                    StringBuilder sb = getFloorsInfo(fl);
                    info.setText(sb.toString());
                }
                catch(NumberFormatException ex)
                {
                    info.setText("Numeri inseriti errati");
                }
            }
        });
        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
        bottomPanel.add(new JPanel()); bottomPanel.add(create); bottomPanel.add(new JPanel());
        setFont(topPanel, new Font("Helvetica", Font.PLAIN, 30));
        setFont(info, new Font("Helvetica", Font.PLAIN, 40));
        setFont(bottomPanel, new Font("Helvetica", Font.PLAIN, 30));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel removeFloorCard()
    {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        JTextField jf1 = new JTextField("Piano da rimuovere: ");
        jf1.setEditable(false);
        JTextField floor = new JTextField();
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setLineWrap(true);
        //Reinizializzo JTextArea quando cambio card
        card.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                info.setText("");
            }
        });
        JScrollPane scroll = new JScrollPane(info);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        topPanel.add(jf1); topPanel.add(floor);
        topPanel.setPreferredSize(new Dimension(500,50));
        JButton remove = new JButton("Rimuovi");
        remove.setPreferredSize(new Dimension(200,100));
        remove.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    man.removeFloor(Integer.parseInt(floor.getText()));
                    ArrayList<Floor> fl = man.getFloorsList();
                    StringBuilder sb = getFloorsInfo(fl);
                    info.setText(sb.toString());
                }
                catch(NumberFormatException ex)
                {
                    info.setText("Numeri inseriti errati");
                }
            }
        });
        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
        bottomPanel.add(new JPanel()); bottomPanel.add(remove); bottomPanel.add(new JPanel());
        setFont(topPanel, new Font("Helvetica", Font.PLAIN, 30));
        setFont(info, new Font("Helvetica", Font.PLAIN, 40));
        setFont(bottomPanel, new Font("Helvetica", Font.PLAIN, 30));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel chooseTariffCard()
    {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        JTextField jf1 = new JTextField("Tariffa: ");
        jf1.setEditable(false);
        JTextField tariff = new JTextField();
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setLineWrap(true);
        //Reinizializzo JTextArea quando cambio card
        card.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                info.setText("");
            }
        });
        topPanel.add(jf1); topPanel.add(tariff);
        topPanel.setPreferredSize(new Dimension(500,50));
        JButton create = new JButton("Scegli");
        create.setPreferredSize(new Dimension(200,100));
        create.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    man.setTariff(Integer.parseInt(tariff.getText()));
                    String st = "Tariffa attuale: " + man.getTariff();
                    info.setText(st);
                }
                catch(NumberFormatException ex)
                {
                    info.setText("Numeri inseriti errati");
                }
            }
        });
        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
        bottomPanel.add(new JPanel()); bottomPanel.add(create); bottomPanel.add(new JPanel());
        setFont(topPanel, new Font("Helvetica", Font.PLAIN, 30));
        setFont(info, new Font("Helvetica", Font.PLAIN, 40));
        setFont(bottomPanel, new Font("Helvetica", Font.PLAIN, 30));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel chooseSubdivisionCard()
    {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        JTextField jf1 = new JTextField("Posti riservati abbonamento: ");
        jf1.setEditable(false);
        JTextField sub = new JTextField();
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setLineWrap(true);
        //Reinizializzo JTextArea quando cambio card
        card.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                info.setText("");
            }
        });
        topPanel.add(jf1); topPanel.add(sub);
        topPanel.setPreferredSize(new Dimension(500,50));
        JButton create = new JButton("Scegli");
        create.setPreferredSize(new Dimension(200,100));
        create.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    man.setSpacesSubdivision(Integer.parseInt(sub.getText()));
                    String st = "Posti totali: " + man.getFreeSpacesTot() + "\n" + "Posti abbonati: " + man.getFreeSpacesSubTot() + ", posti ticket: " + man.getFreeSpacesTicketTot();
                    info.setText(st);
                }
                catch(NumberFormatException ex)
                {
                    info.setText("Numeri inseriti errati");
                }
                catch(SubdivisionException ex)
                {
                    info.setText(ex.getMessage());
                }
            }
        });
        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
        bottomPanel.add(new JPanel()); bottomPanel.add(create); bottomPanel.add(new JPanel());
        setFont(topPanel, new Font("Helvetica", Font.PLAIN, 30));
        setFont(info, new Font("Helvetica", Font.PLAIN, 40));
        setFont(bottomPanel, new Font("Helvetica", Font.PLAIN, 30));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(info, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel showDriversInfo()
    {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 2));
        JTextField jf1 = new JTextField("Scegli categoria clienti: ");
        jf1.setEditable(false);
        String comboBoxItems[] = {"Solo clienti normali", "Solo abbonati", "Tutti"};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setLineWrap(true);
        //Reinizializzo JTextArea quando cambio card
        card.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                info.setText("");
            }
        });
        topPanel.add(jf1); topPanel.add(cb);
        topPanel.setPreferredSize(new Dimension(500,50));
        JButton create = new JButton("Scegli");
        create.setPreferredSize(new Dimension(200,100));
        create.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Driver> drivers = man.getDrivers();
                ArrayList<Driver> subs = man.getSubDrivers();
                String sw = (String)cb.getSelectedItem();
                switch(sw)
                {
                    case "Solo clienti normali":
                        StringBuilder sb = new StringBuilder();
                        for (Driver d : drivers)
                        {
                            sb.append(getDriverInfo(d));
                            sb.append("\n");
                        }
                        info.setText(sb.toString());
                        break;
                    case "Solo abbonati":
                        StringBuilder sb2 = new StringBuilder();
                        for (Driver d : subs)
                        {
                            sb2.append(getDriverInfo(d));
                            sb2.append("\n");
                        }
                        info.setText(sb2.toString());
                        break;
                    case "Tutti":
                        StringBuilder sb3 = new StringBuilder();
                        for (Driver d : drivers)
                        {
                            sb3.append(getDriverInfo(d));
                            sb3.append("\n");
                        }
                        for (Driver d : subs)
                        {
                            sb3.append(getDriverInfo(d));
                            sb3.append("\n");
                        }
                        info.setText(sb3.toString());
                        break;
                }
            }
        });
        JScrollPane scroll = new JScrollPane(info);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel bottomPanel = new JPanel(new GridLayout(1,3));
        bottomPanel.add(new JPanel()); bottomPanel.add(create); bottomPanel.add(new JPanel());
        setFont(topPanel, new Font("Helvetica", Font.PLAIN, 30));
        setFont(info, new Font("Helvetica", Font.PLAIN, 20));
        setFont(bottomPanel, new Font("Helvetica", Font.PLAIN, 30));
        card.add(topPanel, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    //******************************************************

    public void itemStateChanged(ItemEvent evt)
    {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }

    private StringBuilder getFloorsInfo(ArrayList<Floor> fl)
    {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<fl.size();i++)
        {
            sb.append("Piano ");
            sb.append(fl.get(i).getId());
            sb.append(", posti ");
            sb.append(fl.get(i).getFreeSpace());
            sb.append("\n");
        }
        return sb;
    }

    private String getDriverInfo(Driver d)
    {
        String s = "Cliente: " + d.getCarId() + ", ingresso: " + d.getTimeIn().toZonedDateTime().toString() + ", pagato: " + (d.getTimePaid()==null?"No":d.getTimePaid().toZonedDateTime().toString()) + ", abbonamento: " + (d.printSub()==null?"No":d.printSub());
        return s;
    }

    private void setFont(Component comp, Font font)
    {
        comp.setFont(font);
        for (Component child : ((Container)comp).getComponents())
        {
            child.setFont(font);
        }
    }
}