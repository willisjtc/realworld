create sequence realworld_id_sequence;

CREATE or replace FUNCTION next_id(OUT "result" bpchar) RETURNS "bpchar"
AS $BODY$
DECLARE
    seq_id bigint;
    shard_id int =  ${shard_num};
    calc bigint;
    foo text;
BEGIN
    SELECT nextval('${schema}.realworld_id_sequence') INTO seq_id;
    calc = seq_id << 16;
    calc = calc | shard_id;
    result = public.stringify_bigint(calc);
END;
$BODY$
    LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION public.stringify_bigint(n int8) RETURNS "text"
AS $BODY$
DECLARE
    alphabet text:='BCDFGHJKLMNPQRSTVWXYZ123456789';
    alpha char[];
    base int:=char_length(alphabet);
    rem bigint := 0;
    output text:='';
BEGIN
    alpha := string_to_array(alphabet, null);
    IF n = 0 THEN RETURN alpha[0]; END IF;
    LOOP
        rem := n % base;
        n := (n / base)::bigint;
        output := alpha[rem+1] || output;
        EXIT WHEN n = 0;
    END LOOP;
    RETURN output;
END $BODY$
    LANGUAGE plpgsql;

CREATE TABLE articles (
    id varchar(16) not null default next_id() primary key,
    data jsonb not null check (array_length(regexp_split_to_array(data->>'slug', E'\\s+'), 1) = 1)
);

CREATE TABLE comments (
    id varchar(16) not null default next_id() primary key,
    data jsonb not null
);

CREATE TABLE users (
    id varchar(16) not null default next_id() primary key,
    data jsonb not null
);

CREATE TABLE follows (
    data jsonb not null primary key
);

CREATE TABLE favorites (
    data jsonb not null primary key
);

CREATE TABLE tags (
    data jsonb not null primary key
);

CREATE TABLE article_tags (
    data jsonb not null primary key
);


create unique index articles_slug_idx on articles((data->>'slug'));
create index articles_title_idx on articles((data->>'title'));
create index articles_userid_idx on articles((data->>'userId'));

create unique index users_email_idx on users((data->>'email'));
create unique index users_username_idx on users((data->>'username'));

create index user_following_idx on follows((data->>'followingUserId'));
create index user_followed_idx on follows((data->>'followedUserId'));

create index comments_username_idx on comments((data->>'userId'));

create or replace function article_slug_maker(article_title varchar) RETURNS integer AS $function$
declare
    num_of_same_title integer;
    slug varchar;
begin
    select count(1) from articles where data->>'title' = article_title INTO num_of_same_title;
    select string_agg(word, '-') || '-' || (num_of_same_title + 1) from (select regexp_split_to_table(article_title, E'\\s+') as word) split INTO slug;
    return slug;
end;
$function$
language plpgsql volatile
cost 100;

