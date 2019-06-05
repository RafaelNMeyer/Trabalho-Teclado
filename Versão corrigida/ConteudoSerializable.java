import java.io.Serializable;

public class ConteudoSerializable implements Serializable
{
	private String frase;
	private int tamanho; // length da string do pangrama
	private int acertos;
	private int erros;

// no-argument constructor calls other constructor with default values
public ConteudoSerializable() 
{
} // end no-argument AccountRecordSerializable constructor

// four-argument constructor initializes a record
public ConteudoSerializable( String f, int a, int t, int e )
{
   setFrase( f );
   setAcertos(a);
   setTamanho (t);
   setErros(e);

} // end four-argument AccountRecordSerializable constructor

public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public int getAcertos() {
		return acertos;
	}

	public void setAcertos(int acertos) {
		this.acertos = acertos;
	}

	public int getErros() {
		return erros;
	}

	public void setErros(int erros) {
		this.erros = erros;
	}

}
