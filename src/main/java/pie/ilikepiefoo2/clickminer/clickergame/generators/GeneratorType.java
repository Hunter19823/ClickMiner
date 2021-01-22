package pie.ilikepiefoo2.clickminer.clickergame.generators;


import pie.ilikepiefoo2.clickminer.clickergame.Generator;

public enum GeneratorType
{
    CLICK(ClickGenerator.class);

    private Class parent;
    GeneratorType(Class<? extends Generator> parent)
    {
        this.parent = parent;
    }

    public Class getParent()
    {
        return parent;
    }
}
