class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.string :team1
      t.string :team2
      t.string :tip
      t.string :category
      t.date   :date

      t.timestamps

      t.index :category
    end
  end
end
