CREATE TABLE time_cartola (
                              id BIGINT PRIMARY KEY,
                              nome VARCHAR(255) NOT NULL,
                              nome_cartola VARCHAR(255),
                              slug VARCHAR(255),
                              url_escudo TEXT,
                              atualizado_em TIMESTAMP WITH TIME ZONE NOT NULL
);

ALTER TABLE escalacao
    ADD CONSTRAINT fk_escalacao_time_cartola
    FOREIGN KEY (time_id)
        REFERENCES time_cartola (id);